package com.juliy.blog;

import com.juliy.service.MenuService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringbootApplicationTests {

    @Autowired
    MenuService menuService;

    @Test
    void testMenuService() {

    }

}
