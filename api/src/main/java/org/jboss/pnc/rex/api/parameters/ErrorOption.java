/**
 * JBoss, Home of Professional Open Source.
 * Copyright 2021-2024 Red Hat, Inc., and individual contributors
 * as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.pnc.rex.api.parameters;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(name = "ErrorOption", description = "The possible error options")
public enum ErrorOption {
    /**
     * DEFAULT option
     *
     * In case there are unrecoverable errors, return ErrorResponse and appropriate status code.
     */
    PASS_ERROR,

    /**
     * In some cases (TaskMissing) return positive status code even if it's not default behaviour.
     */
    IGNORE
}
