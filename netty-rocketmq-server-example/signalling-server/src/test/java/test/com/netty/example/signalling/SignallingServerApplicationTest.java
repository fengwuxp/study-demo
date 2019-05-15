package test.com.netty.example.signalling;


import com.netty.example.server.SignallingServerApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SignallingServerApplication.class})
public class SignallingServerApplicationTest {


    @Test
    public void testRunMain() throws Exception {

    }
}
