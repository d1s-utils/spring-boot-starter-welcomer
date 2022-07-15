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

package dev.d1s.welcomer.testUtil

import com.fasterxml.jackson.databind.ObjectMapper
import dev.d1s.teabag.testing.constant.VALID_STUB
import dev.d1s.welcomer.constant.DEFAULT_MESSAGE
import dev.d1s.welcomer.properties.WelcomerConfigurationProperties
import dev.d1s.welcomer.service.impl.WelcomerServiceImpl
import io.mockk.every
import org.springframework.boot.actuate.info.InfoEndpoint

internal const val DUMMY_PROPERTY_1 = "p1"
internal const val DUMMY_PROPERTY_2 = "p2"

internal const val PADDING = 0

internal fun WelcomerServiceImpl.configure() {
    every {
        getContent()
    } returns VALID_STUB
}

internal fun WelcomerConfigurationProperties.configure() {
    every {
        message
    } returns DEFAULT_MESSAGE

    every {
        padding
    } returns PADDING

    every {
        excludeProperties
    } returns listOf(DUMMY_PROPERTY_2)
}

internal fun InfoEndpoint.configure() {
    every {
        info()
    } returns mapOf(
        DUMMY_PROPERTY_1 to VALID_STUB,
        DUMMY_PROPERTY_2 to VALID_STUB
    )
}

internal fun ObjectMapper.configure() {
    every {
        writeValueAsString(any())
    } returns VALID_STUB
}