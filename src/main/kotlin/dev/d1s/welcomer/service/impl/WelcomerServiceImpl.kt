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

package dev.d1s.welcomer.service.impl

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import dev.d1s.teabag.stdlib.text.padding
import dev.d1s.welcomer.constant.DEFAULT_MESSAGE
import dev.d1s.welcomer.properties.WelcomerConfigurationProperties
import dev.d1s.welcomer.service.WelcomerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.actuate.info.InfoEndpoint
import org.springframework.stereotype.Service

@Service
internal class WelcomerServiceImpl : WelcomerService {

    @set:Autowired
    lateinit var welcomerConfigurationProperties: WelcomerConfigurationProperties

    @set:Autowired
    lateinit var infoEndpoint: InfoEndpoint

    private val objectMapper = ObjectMapper(
        YAMLFactory().disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER)
    ).registerModule(
        JavaTimeModule()
    ).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)

    override fun getContent() = buildString {
        var appended = false

        welcomerConfigurationProperties.message.let {
            if (it.isNotBlank()) {
                appendLine(welcomerConfigurationProperties.message)
                appendLine()

                appended = true
            }
        }

        val info = infoEndpoint.info()
            .filter {
                !welcomerConfigurationProperties.excludeProperties.contains(it.key)
            }

        if (info.isNotEmpty()) {
            append(objectMapper.writeValueAsString(info))

            appended = true
        }

        if (!appended) {
            append(DEFAULT_MESSAGE)
        }
    }.padding(
        welcomerConfigurationProperties.padding
    )
}