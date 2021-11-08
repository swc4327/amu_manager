package com.awesome.amumanager.data.api.response

import com.awesome.amumanager.domain.model.Promotion


class PromotionResponse(val code: Int, val promotions: ArrayList<Promotion>)