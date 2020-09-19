package test.com.wuxp.study.mockito.spy;

import com.wuxp.study.mockito.spy.SpyHelloDemo;
import org.hamcrest.MatcherAssert;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

public class SpkHelloDemoTest {

    @Spy
    private SpyHelloDemo spyHelloDemo;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = IllegalArgumentException.class)
    public void sayHello() {
        when(spyHelloDemo.sayHello()).thenReturn("hello");
        Assert.assertEquals(spyHelloDemo.sayHello(), "hello");
        doThrow(new IllegalArgumentException()).when(spyHelloDemo).sayHello("1");
        spyHelloDemo.sayHello("1");
        Assert.assertEquals("hello word",spyHelloDemo.sayHello("1"));
    }

    @Test
    public void testSayHello() {
        final String hello = "hello word";
        when(spyHelloDemo.sayHello(hello)).thenReturn(hello);
        Assert.assertEquals(spyHelloDemo.sayHello(hello), hello);
        when(spyHelloDemo.sayHello(hello)).thenThrow(RuntimeException.class);
        Assert.assertThrows(RuntimeException.class, () -> spyHelloDemo.sayHello(hello));
        Assert.assertEquals("你好啊",spyHelloDemo.sayHello("你好啊"));
        Assert.assertNotEquals("你好啊",spyHelloDemo.sayHello("你好"));
        MatcherAssert.assertThat("你好",equalTo(spyHelloDemo.sayHello("你好")));
    }


}