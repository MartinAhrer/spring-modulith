/*
 * Copyright 2023 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.modulith.events.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.modulith.ApplicationModuleListener;
import org.springframework.modulith.events.ConditionalEventListener;
import org.springframework.modulith.events.EventExternalizer;
import org.springframework.modulith.events.EventSerializer;
import org.springframework.modulith.events.externalize.EventExternalizationConfiguration;
import org.springframework.modulith.events.externalize.EventExternalizationConfiguration.RoutingTarget;

/**
 * @author Oliver Drotbohm
 */
abstract class AbstractEventExternalizer implements EventExternalizer, ConditionalEventListener {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private final EventSerializer serializer;
	private final EventExternalizationConfiguration configuration;

	/**
	 * @param serializer
	 * @param configuration
	 */
	protected AbstractEventExternalizer(EventSerializer serializer, EventExternalizationConfiguration configuration) {
		this.serializer = serializer;
		this.configuration = configuration;
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.modulith.events.ConditionalEventListener#supports(java.lang.Object)
	 */
	@Override
	public boolean supports(Object event) {
		return configuration.supports(event);
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.modulith.events.foo.EventExternalizer#externalize(java.lang.Object)
	 */
	@Override
	@ApplicationModuleListener
	public void externalize(Object event) {

		if (!configuration.supports(event)) {
			return;
		}

		var target = configuration.determineTarget(event);
		var mapped = configuration.map(event);
		var serialized = serializer.serialize(mapped);

		if (logger.isTraceEnabled()) {
			logger.trace("Externalizing event of type {} to {}, payload: {}).", event.getClass(), target,
					serialized);
		} else if (logger.isDebugEnabled()) {
			logger.debug("Externalizing event of type {} to {}.", event.getClass(), target);
		}

		externalize(target, serialized);
	}

	protected abstract void externalize(RoutingTarget target, Object payload);
}
