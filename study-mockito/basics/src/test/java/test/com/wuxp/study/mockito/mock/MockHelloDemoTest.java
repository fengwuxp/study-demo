package test.com.wuxp.study.mockito.mock;

import com.wuxp.study.mockito.mock.MockHelloDemo;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class MockHelloDemoTest {

    @Mock()
    private MockHelloDemo mockHelloDemo;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = IllegalArgumentException.class)
    public void sayHello() {
        when(mockHelloDemo.sayHello()).thenReturn("hello");
        assertEquals(mockHelloDemo.sayHello(), "hello");
        doThrow(new IllegalArgumentException()).when(mockHelloDemo).sayHello("1");
        mockHelloDemo.sayHello("1");
    }

    @Test
    public void testSayHello() {
        final String hello = "hello word";
        when(mockHelloDemo.sayHello(hello)).thenReturn(hello);
        assertEquals(mockHelloDemo.sayHello(hello), hello);
        when(mockHelloDemo.sayHello(hello)).thenThrow(RuntimeException.class);
        assertThrows(RuntimeException.class, () -> mockHelloDemo.sayHello(hello));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNoSay() {
        doNothing().when(mockHelloDemo).noSay();
        mockHelloDemo.noSay();
        mockHelloDemo.noSay();
        verify(mockHelloDemo, times(2)).noSay();


        doThrow(new IllegalArgumentException()).when(mockHelloDemo).noSay();
        mockHelloDemo.noSay();
    }

    @Test
    public void testDoReturn() {
        doReturn("hello word").when(mockHelloDemo).sayHello("1");
        assertEquals("hello word", mockHelloDemo.sayHello("1"));
        assertNotEquals("hello word", mockHelloDemo.sayHello("2"));
    }

    @Test
    public void testIterateSubbing() {
        when(mockHelloDemo.sayHello("1")).thenReturn("1", "2", "3").thenReturn("4");
        assertEquals("1", mockHelloDemo.sayHello("1"));
        assertEquals("2", mockHelloDemo.sayHello("1"));
        assertEquals("3", mockHelloDemo.sayHello("1"));
        assertEquals("4", mockHelloDemo.sayHello("1"));
        assertEquals("4", mockHelloDemo.sayHello("1"));
    }

    @Test
    public void testSubbingWithAnswer() {
        when(mockHelloDemo.sayHello(anyString())).thenAnswer((Answer<String>) invocation -> String.format("%s-x", invocation.getArguments()[0]));
        assertEquals("1-x", mockHelloDemo.sayHello("1"));
        assertEquals("2-x", mockHelloDemo.sayHello("2"));
    }

    @Test
    public void testSubbingThen() {
        when(mockHelloDemo.sayHello(anyString())).then(invocation -> invocation.getMethod().getName());
        assertEquals("sayHello", mockHelloDemo.sayHello("1"));
    }

    @Test
    public void testSubbingRealCall() {
        when(mockHelloDemo.sayHello(anyString())).then(invocation -> invocation.getMethod().getName());
        assertEquals("sayHello", mockHelloDemo.sayHello("1"));
        when(mockHelloDemo.sayHello("hello word")).thenCallRealMethod();
        assertEquals("hello word", mockHelloDemo.sayHello("hello word"));
    }
}