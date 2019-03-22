// 
// Decompiled by Procyon v0.5.30
// 

package com.nokia.hal.nbi.connector;

import io.restassured.response.ValidatableResponseOptions;
import io.restassured.response.Validatable;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.RestAssured;

class CASSession
{
    String obtain(final String baseHostParam, final String basePathParam, final String portParam, final String usernameParam, final String passwordParam) {
        RestAssured.useRelaxedHTTPSValidation();
        final String stg = ((ValidatableResponseOptions<ValidatableResponse, R>)((Validatable<ValidatableResponse, R>)RestAssured.given().headers("username", usernameParam, "password", passwordParam).when().get(baseHostParam + ":" + portParam + "/hal-nbi/caslogin", new Object[0])).then()).statusCode(200).extract().path("stg", new String[0]);
        return RestAssured.given().params("ticket", stg, new Object[0]).when().get(baseHostParam + ":" + portParam + "/hal-nbi/login", new Object[0]).getSessionId();
    }
}
