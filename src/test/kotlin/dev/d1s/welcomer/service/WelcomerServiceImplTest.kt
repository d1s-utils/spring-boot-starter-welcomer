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

package dev.d1s.welcomer.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import dev.d1s.teabag.stdlib.text.padding
import dev.d1s.teabag.testing.constant.VALID_STUB
import dev.d1s.welcomer.properties.WelcomerConfigurationProperties
import dev.d1s.welcomer.service.impl.WelcomerServiceImpl
import dev.d1s.welcomer.testUtil.DUMMY_PROPERTY_1
import dev.d1s.welcomer.testUtil.configure
import dev.d1s.welcomer.testUtil.mockPaddingFun
import io.mockk.every
import io.mockk.verifyAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.actuate.info.InfoEndpoint
import org.springframework.boot.test.context.SpringBootTest
import strikt.api.expectThat
import strikt.assertions.isEqualTo

@SpringBootTest(
    classes = [WelcomerServiceImpl::class],
    webEnvironment = SpringBootTest.WebEnvironment.NONE
)
internal class WelcomerServiceImplTest {

    @set:Autowired
    lateinit var welcomerServiceImpl: WelcomerServiceImpl

    @MockkBean
    private lateinit var welcomerConfigurationProperties: WelcomerConfigurationProperties

    @MockkBean
    private lateinit var infoEndpoint: InfoEndpoint

    @MockkBean
    private lateinit var yamlObjectMapper: ObjectMapper

    @BeforeEach
    fun setUp() {
        welcomerConfigurationProperties.configure()
        infoEndpoint.configure()
        yamlObjectMapper.configure()
    }

    @Test
    fun `should return valid content`() {
        val expectedContent = "Welcome.\n\n$VALID_STUB"

        mockPaddingFun {
            expectThat(
                welcomerServiceImpl.getContent()
            ) isEqualTo expectedContent

            verifyAll {
                welcomerConfigurationProperties.message
                infoEndpoint.info()
                welcomerConfigurationProperties.excludeProperties
                yamlObjectMapper.writeValueAsString(
                    mapOf(
                        DUMMY_PROPERTY_1 to VALID_STUB
                    )
                )
                welcomerConfigurationProperties.padding
                expectedContent.padding(welcomerConfigurationProperties.padding)
            }
        }
    }

    @Test
    fun `should return only default message in case of data absence`() {
        every {
            welcomerConfigurationProperties.message
        } returns ""

        every {
            infoEndpoint.info()
        } returns mapOf()

        val expectedContent = "Welcome."

        mockPaddingFun {
            expectThat(
                welcomerServiceImpl.getContent()
            ) isEqualTo expectedContent

            verifyAll {
                welcomerConfigurationProperties.message
                infoEndpoint.info()
                welcomerConfigurationProperties.padding
                expectedContent.padding(welcomerConfigurationProperties.padding)
            }
        }
    }
}