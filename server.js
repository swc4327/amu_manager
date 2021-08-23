const express = require('express');
const db = require('mysql');
const app = express();

app.listen('8080', () => {
    console.log('Server Started');
});

var dbc = db.createConnection({
    host: "localhost",
    database: "amudb",
    user: "root",
    password: "zz3302"
});

dbc.connect((err) => {
    if (err) throw err;
    console.log('Database Connected');
});

app.use(express.urlencoded({ extended: false }));
app.use(express.json());


app.get('/', (req, res) => {
    res.send(200).send('Backend');
});

app.put('/openStore', (req, res) => {
    console.log("----------Open Store----------");
    var query = `UPDATE store SET isOpened="1" WHERE id = "${req.query.storeId}";`
    dbc.query(query, (err) => {
        if (err) return console.log(err);
        res.send({ code: 200 });
    });

});

app.put('/closeStore', (req, res) => {
    console.log("----------Close Store----------");
    var query = `UPDATE store SET isOpened="0" WHERE id = "${req.query.storeId}";`
    dbc.query(query, (err) => {
        if (err) return console.log(err);
        res.send({ code: 200 });
    });

});

app.put('/completeReserve', (req, res) => {
    console.log("----------Complete Reserve----------");
    var query = `UPDATE reservelist SET isCompleted="1" WHERE id = "${req.query.reserveId}";`
    dbc.query(query, (err) => {
        if (err) return console.log(err);
        res.send({ code: 200 });
    });

});

app.put('/confirmReserve', (req, res) => {
    console.log("----------Confirm Reserve----------");
    var query = `UPDATE reservelist SET isConfirmed="1" WHERE id = "${req.query.reserveId}";`
    dbc.query(query, (err) => {
        if (err) return console.log(err);
    });
    res.send({ code: 200 });
});

app.delete('/cancelReserve', (req, res) => {
    console.log("----------Cancel Reserve----------");
    var query = `DELETE FROM reservelist WHERE id = "${req.query.reserveId}";`
    dbc.query(query, (err) => {
        if (err) return console.log(err);
        res.send({ code: 200 });
    });
});


app.get('/getStore', (req, res) => {
    console.log("----------Get Store----------");
    var query = `SELECT * FROM store WHERE managerUid='${req.query.uid}' AND id > '${req.query.lastId}' LIMIT 10`

    console.log("uid: " + req.query.uid);
    console.log("lastId: " + req.query.lastId);

    dbc.query(query, (err, result) => {
        if (err) return console.log(err);
        result = JSON.parse(JSON.stringify(result));
        console.log(result);
        res.send({ code: 200, stores: result });
    });
});

app.get('/getReviewList', (req, res) => {
    console.log("----------Get ReviewList----------");
    if (req.query.lastId == 0) {
        var query = `SELECT * FROM review WHERE storeId='${req.query.storeId}' ORDER BY id DESC LIMIT 10`;
    }
    else {
        var query = `SELECT * FROM review WHERE storeId='${req.query.storeId}' AND id<'${req.query.lastId}' ORDER BY id DESC LIMIT 10`;
    }

    console.log("storeId: " + req.query.storeId);
    console.log("lastId: " + req.query.lastId);

    dbc.query(query, (err, result) => {
        if (err) return console.log(err);
        result = JSON.parse(JSON.stringify(result));
        console.log(result);
        res.send({ code: 200, reviews: result });
    });
});

app.get('/getReserveList', (req, res) => {
    console.log("----------Get ReserveList----------");
    if (req.query.lastId == 0) {
        var query = `SELECT * FROM reservelist WHERE storeId='${req.query.storeId}' ORDER BY id DESC LIMIT 10`;
    }
    else {
        var query = `SELECT * FROM reservelist where storeId='${req.query.storeId}' AND id <'${req.query.lastId}' ORDER BY id DESC LIMIT 10`;
    }

    console.log("storeId: " + req.query.storeId);

    dbc.query(query, (err, result) => {
        if (err) return console.log(err);
        result = JSON.parse(JSON.stringify(result));
        console.log(result);
        res.send({ code: 200, reserves: result });
    });
});

app.get('/getPromotionList', (req, res) => {
    console.log("----------Get Promotion----------");
    if (req.query.lastId == 0) {
        var query = `SELECT * FROM promotion WHERE storeId = '${req.query.storeId}' ORDER BY id DESC LIMIT 10`;
    } else {
        var query = `SELECT * FROM promotion WHERE storeId = '${req.query.storeId}' AND id < '${req.query.lastId}' ORDER BY id DESC LIMIT 10`;
    }

    console.log("storeId: " + req.query.storeId);

    dbc.query(query, (err, result) => {
        if (err) return console.log(err);
        result = JSON.parse(JSON.stringify(result));
        console.log(result);
        res.send({ code: 200, promotions: result });
    });
});

app.delete('/endPromotion', (req, res) => {
    console.log("----------End Promotion----------");
    var query = `DELETE FROM promotion WHERE id = "${req.query.promotionId}";`
    dbc.query(query, (err) => {
        if (err) return console.log(err);
        res.send({ code: 200 });
    });
});

app.get('/getMenuList', (req, res) => {
    console.log("----------Get Menu----------");
    var query = `SELECT * FROM menu WHERE storeId='${req.query.storeId}' AND id>'${req.query.lastId}' LIMIT 10`;

    console.log("storeId: " + req.query.storeId);

    dbc.query(query, (err, result) => {
        if (err) return console.log(err);
        result = JSON.parse(JSON.stringify(result));
        console.log(result);
        res.send({ code: 200, menus: result });
    });
});

app.delete('/deleteMenu', (req, res) => {
    console.log("----------Delete Menu----------");
    var query = `DELETE FROM menu WHERE id = "${req.query.menuId}";`
    dbc.query(query, (err) => {
        if (err) return console.log(err);
        res.send({ code: 200 });
    });
});


app.delete('/deleteStore', (req, res) => {
    console.log("----------Delete Store----------");
    var query = `DELETE FROM store WHERE id = "${req.query.storeId}";`
    dbc.query(query, (err) => {
        if (err) return console.log(err);
        res.send({ code: 200 });
    });
});


app.delete('/reviewFiltering', (req, res) => {
    console.log("----------Review Filtering----------");
    var query = `SELECT point FROM review WHERE id='${req.query.reviewId}'`;
    dbc.query(query, (err, result) => {
        if (err) return console.log(err);
        else {
            var deletePoint = result[0].point;
            console.log("deletePoint: " + deletePoint);

            query = `SELECT count, point FROM store WHERE id = "${req.query.storeId}";`
            dbc.query(query, (err, result) => {
                if (err) return console.log(err);
                else {
                    var count = result[0].count;
                    var point = result[0].point;
                    console.log("count: " + count);
                    console.log("point: " + point);
                    var pointSum = (count * point) - parseFloat(deletePoint);
                    console.log("deletePoint " + deletePoint);
                    console.log("pointSum: " + pointSum);
                    count -= 1;
                    if (count == 0) {
                        point = 0;
                    }
                    else {
                        point = pointSum / count;
                    }

                    query = `UPDATE store SET count="${count}", point="${point}" WHERE id = "${req.query.storeId}";`
                    dbc.query(query, (err) => {
                        if (err) return console.log(err);
                    });
                }
            });
        }
    });

    query = `DELETE FROM review WHERE id='${req.query.reviewId}'`;
    dbc.query(query, (err) => {
        if (err) return console.log(err);
    });
    res.send({ code: 200 });
});

app.get('/getClient', (req, res) => {
    console.log("----------Get Client----------");
    var query = `SELECT * FROM client WHERE uid='${req.query.clientId}'`;
    console.log("clientId: " + req.query.clientId);

    dbc.query(query, (err, result) => {
        if (err) return console.log(err);
        result = JSON.parse(JSON.stringify(result));
        console.log(result[0]);
        res.send({ code: 200, client: result[0] });
    });
});


app.post('/manager', (req, res) => {
    console.log("----------Post Manager----------");
    var query = `INSERT INTO manager VALUES ("${req.body.uid}", "${req.body.nickname}", "${req.body.image}")`;
    dbc.query(query, (err) => {
        if (err) return console.log(err);
    });
    res.send({ code: 200 });
});


app.post('/store', (req, res) => {
    console.log("----------Post Store----------");
    var query = `INSERT INTO store VALUES(NULL, "${req.body.name}", "${req.body.image}", "${req.body.managerUid}", "${req.body.lat}"
        , "${req.body.lng}", "${req.body.place}", "${req.body.placeDetail}", "${req.body.kind}", 0, 0, "${req.body.isOpened}")`;
    dbc.query(query, (err) => {
        if (err) return console.log(err);
    });
    res.send({ code: 200 });
});



app.post('/menu', (req, res) => {
    console.log("----------Post Menu----------");
    var query = `INSERT INTO menu VALUES(NULL, "${req.body.name}", "${req.body.image}", "${req.body.price}", "${req.body.comment}", "${req.body.storeId}")`;
    dbc.query(query, (err) => {
        if (err) return console.log(err);
    });
    res.send({ code: 200 });
});

app.post('/promotion', (req, res) => {
    console.log("----------Post Promotion----------");
    var query = `INSERT INTO promotion VALUES(NULL, now(), "${req.body.storeName}", "${req.body.message}", "${req.body.storeId}")`;

    console.log("message: " + req.body.message);

    dbc.query(query, (err) => {
        if (err) return console.log(err);
    });
    res.send({ code: 200 });
});

app.get('/getConfirmedReserveList', (req, res) => {
    console.log("----------Get Confirmed ReserveList----------");
    if (req.query.lastId == 0) {
        var query = `SELECT * FROM reservelist WHERE storeId='${req.query.storeId}' AND isConfirmed=1 AND isCompleted=0 ORDER BY id DESC LIMIT 10`;
    }
    else {
        var query = `SELECT * FROM reservelist where storeId='${req.query.storeId}' AND id <'${req.query.lastId}' AND isConfirmed=1 AND isCompleted=0 ORDER BY id DESC LIMIT 10`;
    }

    console.log("storeId: " + req.query.storeId);

    dbc.query(query, (err, result) => {
        if (err) return console.log(err);
        result = JSON.parse(JSON.stringify(result));
        console.log(result);
        res.send({ code: 200, reserves: result });
    });
});

app.get('/getStoreForClient', (req, res) => {
    console.log("----------Get Store For Client----------");

    var query = `SELECT *,
	(6371*acos(cos(radians('${req.query.lat}'))*cos(radians(lat))*cos(radians(lng)
	-radians('${req.query.lng}'))+sin(radians('${req.query.lat}'))*sin(radians(lat))))
	AS distance FROM store WHERE kind='${req.query.kind}' AND id > '${req.query.lastId}' HAVING distance <= 5.0 LIMIT 10`;
    dbc.query(query, (err, result) => {
        if (err) return console.log(err);
        result = JSON.parse(JSON.stringify(result));
        console.log(result);
        res.send({ code: 200, stores: result });
    });

});


app.get('/getReserveListC', (req, res) => {
    console.log("----------Get ReserveList For Client----------");
    if (req.query.lastId == 0) {
        var query = `SELECT * FROM reservelist WHERE clientId='${req.query.clientId}' ORDER BY id DESC LIMIT 10`;
    }
    else {
        var query = `SELECT * FROM reservelist where clientId='${req.query.clientId}' AND id <'${req.query.lastId}' ORDER BY id DESC LIMIT 10`;
    }


    console.log("clientId: " + req.query.clientId);

    dbc.query(query, (err, result) => {
        if (err) return console.log(err);
        result = JSON.parse(JSON.stringify(result));
        console.log(result);
        res.send({ code: 200, reserves: result });
    });
});

app.post('/addReview', (req, res) => {
    console.log("----------Add Review----------");
    var query = `INSERT INTO review VALUES (NULL, "${req.body.reviewImage}", "${req.body.comment}", now(), "${req.body.storeId}", "${req.body.clientId}", "${req.body.point}", "${req.body.reserveId}");`
    dbc.query(query, (err) => {
        if (err) return console.log(err);
    })

    query = `UPDATE reservelist SET isReviewed='1' WHERE id =${req.body.reserveId};`
    dbc.query(query, (err) => {
        if (err) return console.log(err);
    })

    query = `SELECT count, point FROM store WHERE id = "${req.body.storeId}";`
    dbc.query(query, (err, result) => {
        if (err) return console.log(err);
        else {
            var count = result[0].count;
            var point = result[0].point;
            console.log("count: " + count);
            console.log("point: " + point);
            var pointSum = (count * point) + parseFloat(req.body.point);
            console.log("req.body.point: " + req.body.point);

            console.log("pointSum: " + pointSum);
            count += 1;
            point = pointSum / count;

            query = `UPDATE store SET count="${count}", point="${point}" WHERE id = "${req.body.storeId}";`
            dbc.query(query, (err) => {
                if (err) return console.log(err);
            });
        }
    });

    query = `SELECT count, point FROM client WHERE uid = "${req.body.clientId}";`
    dbc.query(query, (err, result) => {
        if (err) return console.log(err);
        else {
            var count = result[0].count;
            var point = result[0].point;
            console.log("count: " + count);
            console.log("point: " + point);
            var pointSum = (count * point) + parseFloat(req.body.point);
            console.log("req.body.point: " + req.body.point)

            console.log("pointSum: " + pointSum);
            count += 1;
            point = pointSum / count;

            query = `UPDATE client SET count="${count}", point="${point}" WHERE uid = "${req.body.clientId}";`
            dbc.query(query, (err) => {
                if (err) return console.log(err);
                res.send({ code: 200 });
            });
        }
    });
});

app.get('/getStoreInfo', (req, res) => {
    console.log("----------Get Store Info----------");
    var query = `SELECT * FROM store WHERE id='${req.query.storeId}'`;


    dbc.query(query, (err, result) => {
        if (err) return console.log(err);
        result = JSON.parse(JSON.stringify(result));
        console.log(result[0]);
        res.send({ code: 200, store: result[0] });
    });
});


app.post('/reserve', (req, res) => {
    console.log("----------Post Reserve----------");
    var query = `INSERT INTO reservelist VALUES (NULL, "${req.body.name}", "${req.body.phone}", "${req.body.arrive}", "${req.body.request}", "${req.body.lat}", "${req.body.lng}", now(), "${req.body.storeId}", "${req.body.clientId}", "${req.body.isReviewed}","${req.body.isConfirmed}", "${req.body.isCompleted}")`;
    dbc.query(query, (err) => {
        if (err) return console.log(err);
    });
    res.send({ code: 200 });
});

app.post('/client', (req, res) => {
    console.log("----------Post Client----------");
    var query = `INSERT INTO client VALUES ("${req.body.uid}", "${req.body.nickname}", "${req.body.image}", "${req.body.count}", "${req.body.point}")`;
    dbc.query(query, (err) => {
        if (err) return console.log(err);
    });
    res.send({ code: 200 });
});

app.get('/getVisitedStore', (req, res) => {
    console.log("----------Get Visited Store----------");
    var query = `SELECT * FROM STORE WHERE id IN (SELECT DISTINCT storeId FROM reservelist WHERE clientId = '${req.query.clientId}' AND isReviewed = "1")`;
    console.log("clientId: " + req.query.clientId);

    dbc.query(query, (err, result) => {
        if (err) return console.log(err);
        result = JSON.parse(JSON.stringify(result));
        console.log(result);
        res.send({ code: 200, stores: result });
    });
});

/*
DROP DATABASE IF EXISTS `amudb`;
CREATE DATABASE `amudb`;
USE `amudb`;

CREATE TABLE `manager` (
  `uid` varchar(255) NOT NULL,
  `nickname` varchar(100) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `store` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `managerUid` varchar(255) DEFAULT NULL,
  `lat` varchar(30) DEFAULT NULL,
  `lng` varchar(30) DEFAULT NULL,
  `place` varchar(50) DEFAULT NULL,
  `placeDetail` varchar(50) DEFAULT NULL,
  `kind` varchar(30) DEFAULT NULL,
  `count` int(11) DEFAULT NULL,
  `point` DECIMAL(3,2) DEFAULT NULL,
  `isOpened` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`managerUid`) REFERENCES `manager` (uid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `price` varchar(30) DEFAULT NULL,
  `comment` varchar(30) DEFAULT NULL,
  `storeId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`storeId`) REFERENCES `store` (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



CREATE TABLE `client` (
  `uid` varchar(255) NOT NULL,
  `nickname` varchar(100) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `count` int(11) DEFAULT NULL,
  `point` DECIMAL(3,2) DEFAULT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `promotion` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` varchar(100) DEFAULT NULL,
  `storeName` varchar(100) DEFAULT NULL,
  `message` varchar(255) DEFAULT NULL,
  `storeId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`storeId`) REFERENCES `store` (id) ON DELETE CASCADE
  ) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `reservelist` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `phone` varchar(100) DEFAULT NULL,
  `arrive` varchar(100) DEFAULT NULL,
  `request` varchar(300) DEFAULT NULL,
  `lat` varchar(30) DEFAULT NULL,
  `lng` varchar(30) DEFAULT NULL,
  `date` varchar(100) DEFAULT NULL,
  `storeId` int(11) DEFAULT NULL,
  `clientId` varchar(255) DEFAULT NULL,
  `isReviewed` varchar(30) DEFAULT NULL,
  `isConfirmed` varchar(30) DEFAULT NULL,
  `isCompleted` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`storeId`) REFERENCES `store` (id) ON DELETE CASCADE,
  FOREIGN KEY (`clientId`) REFERENCES `client` (uid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `review` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `reviewImage` varchar(255) DEFAULT NULL,
  `comment` varchar(100) DEFAULT NULL,
  `time` varchar(100) DEFAULT NULL,
  `storeId` int(11) DEFAULT NULL,
  `clientId` varchar(255) DEFAULT NULL,
  `point` varchar(100) DEFAULT NULL,
  `reserveId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`storeId`) REFERENCES `store` (id) ON DELETE CASCADE,
  FOREIGN KEY (`clientId`) REFERENCES `client` (uid),
  FOREIGN KEY (`reserveId`) REFERENCES `reservelist` (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
*/