package test.io.ticktok.server.auth;

import io.ticktok.server.auth.AuthTokenExtractor;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;

import static io.ticktok.server.auth.AuthTokenExtractor.AUTH_HEADER;
import static io.ticktok.server.auth.AuthTokenExtractor.AUTH_PARAM;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AuthTokenExtractorTest {

    private final HttpServletRequest request = mock(HttpServletRequest.class);

    @Test
    void retrieveEmptyStringOnNoToken() {
        when(request.getParameter(AUTH_PARAM)).thenReturn("");
        assertExtractTokenIs("");
    }

    private void assertExtractTokenIs(String token) {
        assertThat(new AuthTokenExtractor(request).extract(), is(token));
    }

    @Test
    void retrieveAccessTokenQueryParam() {
        when(request.getParameter(AUTH_PARAM)).thenReturn("1122");
        assertExtractTokenIs("1122");
    }

    @Test
    void retrieveTokenFromHeader() {
        when(request.getHeader(AUTH_HEADER)).thenReturn("token 1122");
        assertExtractTokenIs("1122");
    }

    @Test
    void supportUpperCaseLetters() {
        when(request.getHeader(AUTH_HEADER)).thenReturn("token AaSDF");
        assertExtractTokenIs("AaSDF");
    }
}