/**
 * JBoss, Home of Professional Open Source.
 * Copyright 2021-2021 Red Hat, Inc., and individual contributors
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
package org.jboss.pnc.rex.core.counter;

import org.infinispan.client.hotrod.MetadataValue;

/**
 * Interface for interacting with counter. Use Metadata versions of get/replace methods to avoid concurrent updates in
 * ISPN.
 */
public interface Counter {

    MetadataValue<Long> getMetadataValue();

    boolean replaceValue(MetadataValue<Long> previousValue, Long newValue);

    @Deprecated
    Long getValue();

    @Deprecated
    boolean replaceValue(Long previousValue, Long value);

    void initialize(Long initialValue);
}