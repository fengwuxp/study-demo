

#### springboot 测试用例相关（这只是一个用于展示测试相关例子，不要在意其他的）
- 简要的演示了SpringBoot 控制器测试、服务器测试、Dao测试(DataJpaTest注解)
- 演示了通过配置SpringBootTest注解的classess属性和webEnvironment属性加快spring启动的速度
```java
@SpringBootTest(classes = {MockitoServiceMockTest.MockitoConfiguration.class}, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class MockitoServiceMockTest {

    @MockBean
    private MemberService memberService;

    @Autowired
    private MockitoService mockitoService;

    @Test
    public void testFindById() {
        Member result = new Member();
        result.setId(1L);
        given(this.memberService.findById(anyLong())).willReturn(result);
        Member member = mockitoService.findById(1L);
        Assertions.assertEquals(result, member);
    }

    @Configuration
    @Import({MockitoService.class, MemberService.class}) // A @Component injected with ExampleService
    static class MockitoConfiguration {
    }
}
```
- 简单演示了MockBen和SpyBean的用法，spring对mockito的支持
- 通过使用h2数据库的内存模式加快测试的启动速度

#### 
- [testable-mock](https://github.com/alibaba/testable-mock)