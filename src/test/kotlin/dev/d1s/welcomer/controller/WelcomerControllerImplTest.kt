/*
 * Copyright 2022 Mikhail Titov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */

package dev.d1s.welcomer.controller

import com.ninjasquad.springmockk.MockkBean
import dev.d1s.teabag.testing.constant.VALID_STUB
import dev.d1s.welcomer.constant.CONTENT_MAPPING
import dev.d1s.welcomer.constant.HOME_MAPPING
import dev.d1s.welcomer.controller.impl.WelcomerControllerImpl
import dev.d1s.welcomer.service.impl.WelcomerServiceImpl
import dev.d1s.welcomer.testUtil.configure
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@WebMvcTest(controllers = [WelcomerControllerImpl::class])
@ContextConfiguration(
    classes = [
        WelcomerControllerImpl::class,
        WelcomerServiceImpl::class
    ]
)
internal class WelcomerControllerImplTest {

    @set:Autowired
    lateinit var mockMvc: MockMvc

    @MockkBean
    private lateinit var welcomerServiceImpl: WelcomerServiceImpl

    @BeforeEach
    fun setUp() {
        welcomerServiceImpl.configure()
    }

    @Test
    fun `should redirect to content page`() {
        mockMvc.get(HOME_MAPPING).andExpect {
            redirectedUrl(CONTENT_MAPPING)

            status {
                isFound()
            }
        }
    }

    @Test
    fun `should return content`() {
        mockMvc.get(CONTENT_MAPPING).andExpect {
            status {
                isOk()
            }

            content {
                contentType("text/plain;charset=UTF-8")
                string(VALID_STUB)
            }
        }

        verify {
            welcomerServiceImpl.getContent()
        }
    }
}