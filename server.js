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

app.put('/completeReserve', (req, res) => {
    var query = `UPDATE reservelist SET is_completed="1" WHERE id = "${req.query.reserveId}";`
    dbc.query(query, (err) => {
        if (err) return console.log(err);
        res.send({ code: 200 });
    });

});

app.put('/confirmReserve', (req, res) => {
    var query = `UPDATE reservelist SET is_confirmed="1" WHERE id = "${req.query.reserveId}";`
    dbc.query(query, (err) => {
        if (err) return console.log(err);
    });
    res.send({ code: 200 });
});

app.delete('/cancelReserve', (req, res) => {
    var query = `DELETE FROM reservelist WHERE id = "${req.query.reserveId}";`
    dbc.query(query, (err) => {
        if (err) return console.log(err);
        res.send({ code: 200 });
    });
});


app.get('/getStore', (req, res) => {
    var query = `SELECT * FROM store WHERE manager_uid='${req.query.uid}' AND id > '${req.query.lastId}' LIMIT 10`

    console.log("uid " + req.query.uid)
    console.log("lastId:" + req.query.lastId)

    dbc.query(query, (err, result) => {
        if (err) return console.log(err);
        console.log(JSON.stringify(result));
        var result_ = JSON.stringify(result);
        result = JSON.parse(result_)
        console.log(JSON.parse(JSON.stringify(result)))
        res.send({ code: 200, stores: result });
    });
});

app.get('/getReviewList', (req, res) => {
    if (req.query.lastId == -1) {
        var query = `SELECT * FROM review WHERE store_id='${req.query.store_id}' ORDER BY id DESC LIMIT 10`;
    }
    else {
        var query = `SELECT * FROM review WHERE store_id='${req.query.store_id}' AND id<'${req.query.lastId}' ORDER BY id DESC LIMIT 10`;
    }

    //console.log(mysql_insert_id())
    console.log("store_id " + req.query.store_id)
    console.log("lastId:" + req.query.lastId)

    dbc.query(query, (err, result) => {
        if (err) return console.log(err);
        console.log(JSON.stringify(result));
        var result_ = JSON.stringify(result);
        result = JSON.parse(result_)
        console.log(JSON.parse(JSON.stringify(result)))
        res.send({ code: 200, reviews: result });
    });
});

app.get('/getReserveList', (req, res) => {
    if (req.query.lastId == -1) {
        var query = `SELECT * FROM reservelist WHERE store_id='${req.query.store_id}' ORDER BY id DESC LIMIT 10`;
    }
    else {
        var query = `SELECT * FROM reservelist where store_id='${req.query.store_id}' AND id <'${req.query.lastId}' ORDER BY id DESC LIMIT 10`;
    }

    console.log("store_id " + req.query.store_id)

    dbc.query(query, (err, result) => {
        if (err) return console.log(err);
        console.log(JSON.stringify(result));
        var result_ = JSON.stringify(result);
        result = JSON.parse(result_)
        console.log(JSON.parse(JSON.stringify(result)))
        res.send({ code: 200, reserves: result });
    });
});

app.get('/getPromotionList', (req, res) => {
    if (req.query.lastId == -1) {
        var query = `SELECT * FROM promotion WHERE store_id = '${req.query.store_id}' ORDER BY id DESC LIMIT 10`;
    } else {
        var query = `SELECT * FROM promotion WHERE store_id = '${req.query.store_id}' AND id < '${req.query.lastId}' ORDER BY id DESC LIMIT 10`;
    }

    console.log("store_id: " + req.query.store_id)

    dbc.query(query, (err, result) => {
        if (err) return console.log(err);
        console.log(JSON.stringify(result));
        var result_ = JSON.stringify(result);
        result = JSON.parse(result_)
        console.log(JSON.parse(JSON.stringify(result)))

        res.send({ code: 200, promotions: result });
    });
});

app.delete('/endPromotion', (req, res) => {
    var query = `DELETE FROM promotion WHERE id = "${req.query.promotionId}";`
    dbc.query(query, (err) => {
        if (err) return console.log(err);
        res.send({ code: 200 });
    });
});

app.get('/getMenuList', (req, res) => {
    var query = `SELECT * FROM menu WHERE store_id='${req.query.store_id}' AND id>'${req.query.lastId}' LIMIT 10`;

    console.log("store_id " + req.query.store_id)

    dbc.query(query, (err, result) => {
        if (err) return console.log(err);
        console.log(JSON.stringify(result));
        var result_ = JSON.stringify(result);
        result = JSON.parse(result_)
        console.log(JSON.parse(JSON.stringify(result)))
        res.send({ code: 200, menus: result });
    });
});

app.delete('/deleteMenu', (req, res) => {
    var query = `DELETE FROM menu WHERE id = "${req.query.menuId}";`
    dbc.query(query, (err) => {
        if (err) return console.log(err);
        res.send({ code: 200 });
    });
});
//cascade
app.delete('/deleteStore', (req, res) => {
    var query = `DELETE FROM store WHERE id = "${req.query.storeId}";`
    dbc.query(query, (err) => {
        if (err) return console.log(err);
        res.send({ code: 200 });
    });
});








// app.get('/getKaraoke', (req, res) => {
//     console.log("karaoke");
//     console.log("lat: " + req.query.lat);
//     console.log("lng: " + req.query.lng);

//     var query = `SELECT *,
// 	(6371*acos(cos(radians('${req.query.lat}'))*cos(radians(lat))*cos(radians(lng)
// 	-radians('${req.query.lng}'))+sin(radians('${req.query.lat}'))*sin(radians(lat))))
// 	AS distance FROM store WHERE kind='${"노래방"}' HAVING distance <= 3.0 ORDER BY distance LIMIT 0,1000`;
//     dbc.query(query, (err, result) => {
//         if (err) return console.log(err);
//         console.log(JSON.stringify(result));
//         var result_ = JSON.stringify(result);
//         result = JSON.parse(result_)
//         console.log(JSON.parse(JSON.stringify(result)))
//         res.send({ code: 200, stores: result });
//     });

// });

// app.get('/getBaseBall', (req, res) => {
//     console.log("baseball");
//     console.log("lat: " + req.query.lat);
//     console.log("lng: " + req.query.lng);

//     var query = `SELECT *,
// 	(6371*acos(cos(radians('${req.query.lat}'))*cos(radians(lat))*cos(radians(lng)
// 	-radians('${req.query.lng}'))+sin(radians('${req.query.lat}'))*sin(radians(lat))))
// 	AS distance FROM store WHERE kind='${"스크린야구장"}' HAVING distance <= 3.0 ORDER BY distance LIMIT 0,1000`;
//     dbc.query(query, (err, result) => {
//         if (err) return console.log(err);
//         console.log(JSON.stringify(result));
//         var result_ = JSON.stringify(result);
//         result = JSON.parse(result_)
//         console.log(JSON.parse(JSON.stringify(result)))
//         res.send({ code: 200, stores: result });
//     });

// });

// app.get('/getBowling', (req, res) => {
//     console.log("bowling");
//     console.log("lat: " + req.query.lat);
//     console.log("lng: " + req.query.lng);

//     var query = `SELECT *,
// 	(6371*acos(cos(radians('${req.query.lat}'))*cos(radians(lat))*cos(radians(lng)
// 	-radians('${req.query.lng}'))+sin(radians('${req.query.lat}'))*sin(radians(lat))))
// 	AS distance FROM store WHERE kind='${"볼링장"}' HAVING distance <= 3.0 ORDER BY distance LIMIT 0,1000`;
//     dbc.query(query, (err, result) => {
//         if (err) return console.log(err);
//         console.log(JSON.stringify(result));
//         var result_ = JSON.stringify(result);
//         result = JSON.parse(result_)
//         console.log(JSON.parse(JSON.stringify(result)))
//         res.send({ code: 200, stores: result });
//     });

// });




app.get('/getStoreForClient', (req, res) => {
    console.log("karaoke");
    console.log("lat: " + req.query.lat);
    console.log("lng: " + req.query.lng);
    console.log("kind:" + req.query.kind);

    var query = `SELECT *,
	(6371*acos(cos(radians('${req.query.lat}'))*cos(radians(lat))*cos(radians(lng)
	-radians('${req.query.lng}'))+sin(radians('${req.query.lat}'))*sin(radians(lat))))
	AS distance FROM store WHERE kind='${req.query.kind}' HAVING distance <= 3.0 ORDER BY distance LIMIT 0,1000`;
    dbc.query(query, (err, result) => {
        if (err) return console.log(err);
        console.log(JSON.stringify(result));
        var result_ = JSON.stringify(result);
        result = JSON.parse(result_)
        console.log(JSON.parse(JSON.stringify(result)))
        res.send({ code: 200, stores: result });
    });

});

app.delete('/reviewFiltering', (req, res) => {
    //store point,리뷰수 갱신
    var query = `SELECT point FROM review WHERE id='${req.query.review_id}'`;
    dbc.query(query, (err, result) => {
        if (err) return console.log(err);
        else {
            var delete_point = result[0].point;
            console.log("delete_point: " + delete_point);


            query = `SELECT count, point FROM store WHERE id = "${req.query.store_id}";`
            dbc.query(query, (err, result) => {
                if (err) return console.log(err);
                else {
                    var count = result[0].count;
                    var point = result[0].point;
                    console.log("count: " + count);
                    console.log("point: " + point);
                    var pointSum = (count * point) - parseFloat(delete_point);
                    console.log("delete_point " + delete_point)
                    console.log("pointSum: " + pointSum);
                    count -= 1;
                    if (count == 0) {
                        point = 0;
                    }
                    else {
                        point = pointSum / count;
                    }

                    query = `UPDATE store SET count="${count}", point="${point}" WHERE id = "${req.query.store_id}";`
                    dbc.query(query, (err) => {
                        if (err) return console.log(err);
                    })
                }
            })
        }
    })

    query = `DELETE FROM review WHERE id='${req.query.review_id}'`;
    dbc.query(query, (err) => {
        if (err) return console.log(err);
    });
    res.send({ code: 200 });

});


app.get('/getReserveListC', (req, res) => {
    var query = `SELECT * FROM reservelist WHERE client_id='${req.query.client_id}' ORDER BY id DESC`;

    console.log("client_id " + req.query.client_id)

    dbc.query(query, (err, result) => {
        if (err) return console.log(err);
        console.log(JSON.stringify(result));
        var result_ = JSON.stringify(result);
        result = JSON.parse(result_)
        console.log(JSON.parse(JSON.stringify(result)))
        res.send({ code: 200, reserves: result });
    });
});

app.post('/addReview', (req, res) => {
    var query = `INSERT INTO review VALUES (NULL, "${req.body.review_image}", "${req.body.comment}", now(), "${req.body.store_id}", "${req.body.client_id}", "${req.body.point}", "${req.body.reserve_id}");`
    dbc.query(query, (err) => {
        if (err) return console.log(err);
    })

    query = `UPDATE reservelist SET is_reviewed='1' WHERE id =${req.body.reserve_id};`
    dbc.query(query, (err) => {
        if (err) return console.log(err);
    })

    query = `SELECT count, point FROM store WHERE id = "${req.body.store_id}";`
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

            query = `UPDATE store SET count="${count}", point="${point}" WHERE id = "${req.body.store_id}";`
            dbc.query(query, (err) => {
                if (err) return console.log(err);
            })
        }
    })

    query = `SELECT count, point FROM client WHERE uid = "${req.body.client_id}";`
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

            query = `UPDATE client SET count="${count}", point="${point}" WHERE uid = "${req.body.client_id}";`
            dbc.query(query, (err) => {
                if (err) return console.log(err);
                res.send({ code: 200 });
            })
        }
    })
})

app.get('/getStoreInfo', (req, res) => {
    var query = `SELECT * FROM store WHERE id='${req.query.store_id}'`;


    dbc.query(query, (err, result) => {
        if (err) return console.log(err);
        // console.log(JSON.stringify(result));
        var result_ = JSON.stringify(result);
        result = JSON.parse(result_)
        // console.log(JSON.parse(JSON.stringify(result)))
        console.log(result[0])
        console.log("***")
        res.send({ code: 200, store: result[0] });
    });
});


app.get('/getClient', (req, res) => {
    var query = `SELECT * FROM client WHERE uid='${req.query.client_id}'`;

    //console.log("client_id** " + uid)
    console.log("getClient start");
    console.log("client_id" + req.query.client_id)

    dbc.query(query, (err, result) => {
        if (err) return console.log(err);
        // console.log(JSON.stringify(result));
        var result_ = JSON.stringify(result);
        result = JSON.parse(result_)
        // console.log(JSON.parse(JSON.stringify(result)))
        console.log(result[0])
        res.send({ code: 200, client: result[0] });
    });
});



app.post('/manager', (req, res) => {
    var query = `INSERT INTO manager VALUES ("${req.body.uid}", "${req.body.nickname}", "${req.body.image}")`;
    dbc.query(query, (err) => {
        if (err) return console.log(err);
    });
    res.send({ code: 200 });
});

app.post('/reserve', (req, res) => {
    var query = `INSERT INTO reservelist VALUES (NULL, "${req.body.name}", "${req.body.phone}", "${req.body.arrive}", "${req.body.request}", "${req.body.lat}", "${req.body.lng}", now(), "${req.body.store_id}", "${req.body.client_id}", "${req.body.is_reviewed}","${req.body.is_confirmed}", "${req.body.is_completed}")`;
    dbc.query(query, (err) => {
        if (err) return console.log(err);
    });
    res.send({ code: 200 });
});

app.post('/client', (req, res) => {
    var query = `INSERT INTO client VALUES ("${req.body.uid}", "${req.body.nickname}", "${req.body.image}", "${req.body.count}", "${req.body.point}")`;
    dbc.query(query, (err) => {
        if (err) return console.log(err);
    });
    res.send({ code: 200 });
});

app.post('/store', (req, res) => {
    var query = `INSERT INTO store VALUES(NULL, "${req.body.name}", "${req.body.image}", "${req.body.manager_uid}", "${req.body.lat}"
        , "${req.body.lng}", "${req.body.place}", "${req.body.place_detail}", "${req.body.kind}", 0, 0)`;
    dbc.query(query, (err) => {
        if (err) return console.log(err);
    });
    res.send({ code: 200 });
});



app.post('/menu', (req, res) => {
    var query = `INSERT INTO menu VALUES(NULL, "${req.body.name}", "${req.body.image}", "${req.body.price}", "${req.body.comment}", "${req.body.store_id}")`;
    dbc.query(query, (err) => {
        if (err) return console.log(err);
    });
    res.send({ code: 200 });
});

app.post('/promotion', (req, res) => {
    var query = `INSERT INTO promotion VALUES(NULL, now(), "${req.body.store_name}", "${req.body.message}", "${req.body.store_id}")`;

    console.log("message:" + req.body.message);

    dbc.query(query, (err) => {
        if (err) return console.log(err);
    });
    res.send({ code: 200 });
});


app.get('/getStoreForPromotion', (req, res) => {
    var query = `SELECT DISTINCT store_id FROM reservelist WHERE client_id = '${req.query.client_id}'`;

    console.log("client_id: " + req.query.client_id)

    dbc.query(query, (err, result) => {
        if (err) return console.log(err);
        console.log(JSON.stringify(result));
        var result_ = JSON.stringify(result);
        result = JSON.parse(result_)
        console.log(JSON.parse(JSON.stringify(result)))

        res.send({ code: 200, storeIds: result });
    });
});

