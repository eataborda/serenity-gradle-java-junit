package com.github.eataborda.api.features.booker;

import com.github.eataborda.api.common.Logger;
import com.github.eataborda.api.enums.Comments;
import com.github.eataborda.api.enums.StatusCode;
import com.github.eataborda.api.steps.BookingSteps;
import io.restassured.response.Response;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.WithTagValuesOf;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(SerenityRunner.class)
@WithTagValuesOf({"regression", "smoke", "post_method"})
public class PostCreateBooking {

    @Steps
    BookingSteps apiSteps;

    @Steps
    Logger l;

    @Test
    public void postCreateBooking() {
        Response response = apiSteps.postCreateBooking();
        apiSteps.validateStatusCode(StatusCode.SC_200.getValue(), response);
        l.log(Comments.SUCCESS_POST.getValue());
        apiSteps.validateResponseBodyIsNotNullAndNotEmpty(response);
        apiSteps.validatePostCreateBookingResponseBodyHasExpectedFields(response);
        apiSteps.validateResponseHeadersAreNotNullAndNotEmpty(response);
        apiSteps.validateResponseHeadersHasExpectedFields(response);
        //Steps after verify the post call
        Response responseAfterCreateBooking = apiSteps.getBookingById(apiSteps.getIdFromCreatedBooking(response));
        apiSteps.validateStatusCode(StatusCode.SC_200.getValue(), responseAfterCreateBooking);
        apiSteps.validateResponseBodyIsNotNullAndNotEmpty(responseAfterCreateBooking);
        apiSteps.validateResponseBodyHasExpectedFields(responseAfterCreateBooking);
        apiSteps.validateResponseBodyHasSameFieldValuesUsedOnRequestBody(responseAfterCreateBooking, "create");
        apiSteps.validateResponseHeadersAreNotNullAndNotEmpty(responseAfterCreateBooking);
        apiSteps.validateResponseHeadersHasExpectedFields(responseAfterCreateBooking);
    }
}
