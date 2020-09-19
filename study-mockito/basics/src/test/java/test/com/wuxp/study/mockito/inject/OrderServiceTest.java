package test.com.wuxp.study.mockito.inject;

import com.wuxp.study.mockito.inject.OrderDao;
import com.wuxp.study.mockito.inject.OrderService;
import com.wuxp.study.mockito.inject.UserDao;
import com.wuxp.study.mockito.inject.dto.OrderInfo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTest {

    @Mock
    private OrderDao orderDao;

    @Spy
    private UserDao userDao;

    @InjectMocks
    private OrderService orderService;

    @Before
    public void setUp() throws Exception {
        when(orderDao.getUserOderIds(anyLong())).thenReturn(Arrays.asList(
                new OrderInfo("1"),
                new OrderInfo("2")
        ));
    }

    @Test
    public void getUserOrders() {
        List<OrderInfo> userOrders = orderService.getUserOrders("admin");
        assertFalse(userOrders.isEmpty());
        assertEquals(userOrders.get(0), new OrderInfo("1"));
    }
}