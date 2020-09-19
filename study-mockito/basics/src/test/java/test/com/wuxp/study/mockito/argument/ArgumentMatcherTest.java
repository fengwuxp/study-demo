package test.com.wuxp.study.mockito.argument;

import lombok.Data;
import org.hamcrest.Matcher;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.Mockito;

public class ArgumentMatcherTest {


    @Test
    public void testArgumentMatch() {
        MemberService memberService = Mockito.mock(MemberService.class);
        Mockito.when(memberService.queryMember(Mockito.isA(MemberQueryRequest.class))).thenReturn("张三");
        Mockito.when(memberService.getAppId(Mockito.any(Request.class))).thenReturn("100");
        Assert.assertEquals("张三", memberService.queryMember(new MemberQueryRequest()));
        Assert.assertEquals("100", memberService.getAppId(new MemberQueryRequest()));
        Mockito.when(memberService.getAppId(Mockito.argThat(new ArgumentMatcher<Request>() {
            @Override
            public boolean matches(Object request) {
                Request req = (Request) request;
                return "001".equals(req.getAppId());
            }
        }))).thenReturn("101");
        MemberQueryRequest request = new MemberQueryRequest();
        request.setAppId("001");
        Assert.assertEquals("101", memberService.getAppId(request));
        Assert.assertNotEquals("101", memberService.getAppId(new MemberCreateRequest()));
    }


    static class MemberService {
        void create(MemberCreateRequest request) {
            System.out.println(request);
        }

        String queryMember(MemberQueryRequest request) {
            System.out.println(request);
            return request.userName;
        }

        String getAppId(Request request) {
            System.out.println(request);
            return request.getAppId();
        }
    }

    interface Request {

        String getAppId();
    }

    @Data
   static abstract class AbstractRequest implements Request {
        private String appId;
    }

    @Data
    static class MemberQueryRequest extends AbstractRequest {
        private String userName;
    }

    @Data
    static class MemberCreateRequest extends AbstractRequest {
        private String userName;
        private String password;
    }
}
