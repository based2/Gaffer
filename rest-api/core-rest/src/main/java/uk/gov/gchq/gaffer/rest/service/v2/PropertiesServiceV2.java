/*
 * Copyright 2016-2017 Crown Copyright
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.gchq.gaffer.rest.service.v2;

import uk.gov.gchq.gaffer.rest.ServiceConstants;

import javax.ws.rs.core.Response;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * An implementation of {@link IPropertiesServiceV2} that gets the configured system properties
 */
public class PropertiesServiceV2 implements IPropertiesServiceV2 {

    private static final String PROPERTIES_LIST = "gaffer.properties";

    @Override
    public Response getProperties() {
        final Map<String, String> properties = new HashMap<>();
        final String propertsList = System.getProperty(PROPERTIES_LIST);
        if (propertsList != null) {
            String[] props = propertsList.split(",");
            for (final String prop : props) {
                properties.put(prop, System.getProperty(prop));
            }
        }
        return Response.ok(Collections.unmodifiableMap(properties))
                .header(ServiceConstants.GAFFER_MEDIA_TYPE_HEADER, ServiceConstants.GAFFER_MEDIA_TYPE).build();
    }

    @Override
    public Response getProperty(final String propertyName) {
        String prop = System.getProperty(propertyName);
        return prop == null ? Response.status(404).header(ServiceConstants.GAFFER_MEDIA_TYPE_HEADER, ServiceConstants.GAFFER_MEDIA_TYPE).build()  : Response.ok(prop).header(ServiceConstants.GAFFER_MEDIA_TYPE_HEADER, ServiceConstants.GAFFER_MEDIA_TYPE).build();
    }

}
