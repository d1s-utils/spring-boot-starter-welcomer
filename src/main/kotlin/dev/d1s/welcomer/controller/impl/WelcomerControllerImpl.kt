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

package dev.d1s.welcomer.controller.impl

import dev.d1s.welcomer.constant.CONTENT_MAPPING
import dev.d1s.welcomer.controller.WelcomerController
import dev.d1s.welcomer.service.WelcomerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.view.RedirectView

@RestController
internal class WelcomerControllerImpl : WelcomerController {

    @set:Autowired
    lateinit var welcomerService: WelcomerService

    override fun redirect() = RedirectView(CONTENT_MAPPING)

    override fun content() = welcomerService.getContent()
}