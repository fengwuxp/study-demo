package test.com.wuxp.study.mockito.when;

import com.wuxp.study.mockito.when.WhenHelloDemo;
import org.junit.Before;
import org.junit.Test;
import org.junit.function.ThrowingRunnable;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class WhenHelloDemoTest {

    @Mock
    private WhenHelloDemo whenHelloDemo;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void sayHello() {
        when(whenHelloDemo.sayHello()).thenReturn("hello");
        assertEquals(whenHelloDemo.sayHello(), "hello");
    }

    @Test
    public void testSayHello() {
        final String hello = "hello word";
        when(whenHelloDemo.sayHello(hello)).thenReturn(hello);
        assertEquals(whenHelloDemo.sayHello(hello), hello);
        when(whenHelloDemo.sayHello(hello)).thenThrow(RuntimeException.class);
        assertThrows(RuntimeException.class, new ThrowingRunnable() {
            public void run() {
                whenHelloDemo.sayHello(hello);
            }
        });
    }
}