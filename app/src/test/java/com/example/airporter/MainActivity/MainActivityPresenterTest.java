package com.example.airporter.MainActivity;

import android.content.Context;

import com.example.airporter.R;
import com.example.airporter.helper.ApiRequestManager;
import com.example.airporter.helper.CONSTANTS;
import com.example.airporter.helper.MyHash;
import com.example.airporter.helper.VolleySeverRequest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MainActivityPresenterTest {

    MainActivityPresenter mainActivityPresenter;
    @Mock
    MainActivityPresenter.View mockView;
    @Mock
    ApiRequestManager apiRequestManagerObject;
    @Captor
    ArgumentCaptor<VolleySeverRequest.VolleyResponseCallback> authenticaeLoginCallbackListener;

    @Before
    public void setup(){
       mainActivityPresenter = new MainActivityPresenter(mockView);
    }

    @Test
    public void checkAuthenticateLogin(){
    }

    @Test
    public void checkEmailValidWithCorrectEmail(){
        String testEmail = "abc@gmail.com";
        mainActivityPresenter.checkEmailValid(testEmail);
        verify(mockView).setLoginButtonState(eq(true), anyBoolean());
    }

    @Test
    public void checkEmailValidWithoutAtTheRate(){
        String testEmail = "abcgmail.com";
        String error = CONSTANTS.DisplayMessages.ERROR_EMAIL_INVALID;
        mainActivityPresenter.checkEmailValid(testEmail);
        verify(mockView).setEmailEditTextError(error);
        verify(mockView).setLoginButtonState(eq(false), anyBoolean());
    }

    @Test
    public void checkEmailValidWithoutDot(){
        String testEmail = "abcg@mailcom";
        String error = CONSTANTS.DisplayMessages.ERROR_EMAIL_INVALID;
        mainActivityPresenter.checkEmailValid(testEmail);
        verify(mockView).setEmailEditTextError(error);
        verify(mockView).setLoginButtonState(eq(false), anyBoolean());
    }

    @Test
    public void checkEmailValidWithEmpty(){
        String testEmail = "";
        String error = CONSTANTS.DisplayMessages.ERROR_EMAIL_EMPTY;
        mainActivityPresenter.checkEmailValid(testEmail);
        verify(mockView).setEmailEditTextError(error);
        verify(mockView).setLoginButtonState(eq(false), anyBoolean());
    }

    @Test
    public void authenticateLogin(){
        mainActivityPresenter.authenticateLogin("email", "passw" , apiRequestManagerObject);
        String encryptedPassword = MyHash.sha256("passw");
        verify(apiRequestManagerObject).authenticateLogin(eq("email"), eq(encryptedPassword), authenticaeLoginCallbackListener.capture(), eq(CONSTANTS.NetworkRequestTags.LOGIN_WITH_EMAIL));
    }
    /*@Test
    public void authenticateLogin() throws JSONException {
        when(jsonResponseObject.getString(anyString())).thenReturn("1");
        when(jsonResponseObject.getJSONArray(anyString())).thenReturn(new JSONArray());

        mainActivityPresenter.authenticateLogin("abc@gmail.com","password", mockApiRequest);
        verify(mockApiRequest).authenticateLogin(eq("abc@gmail.com"),eq(MyHash.sha256("password")), volleyResponseCallbackArgumentCaptor.capture() );
        volleyResponseCallbackArgumentCaptor.getValue().onSuccess(jsonResponseObject);
        assertEquals(1,1);
    }*/

}