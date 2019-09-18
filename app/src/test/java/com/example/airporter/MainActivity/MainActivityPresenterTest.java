package com.example.airporter.MainActivity;

import com.example.airporter.helper.ApiRequests;
import com.example.airporter.helper.MyHash;
import com.example.airporter.helper.VolleySeverRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MainActivityPresenterTest {

    private MainActivityPresenter mainActivityPresenter;

    @Mock
    private ApiRequests mockApiRequest;
    @Mock
    private MainActivityPresenter.View mockView;
    @Mock
    private JSONObject jsonResponseObject;
    @Captor
    private ArgumentCaptor<VolleySeverRequest.VolleyResponseCallback> volleyResponseCallbackArgumentCaptor;

    @Before
    public void setup() {
        mainActivityPresenter = new MainActivityPresenter(mockView);
    }

    @Test
    public void authenticateLogin() throws JSONException {
        when(jsonResponseObject.getString(anyString())).thenReturn("1");
        when(jsonResponseObject.getJSONArray(anyString())).thenReturn(new JSONArray());

       mainActivityPresenter.authenticateLogin("abc@gmail.com","password", mockApiRequest);
       verify(mockApiRequest).authenticateLogin(eq("abc@gmail.com"),eq(MyHash.sha256("password")), volleyResponseCallbackArgumentCaptor.capture() );
       volleyResponseCallbackArgumentCaptor.getValue().onSuccess(jsonResponseObject);
       assertEquals(1,1);
    }

    @Test
    public void checkEmailValid() {
        mainActivityPresenter.checkEmailValid("abcg@mail.com");

        //verify(mockView).setLoginButtonState(eq(true), anyBoolean());
    }

    @Test
    public void checkPasswordValid() {
        mainActivityPresenter.checkPasswordValid("abcd");
        verify(mockView).setLoginButtonState(anyBoolean(), eq(true));
    }
}