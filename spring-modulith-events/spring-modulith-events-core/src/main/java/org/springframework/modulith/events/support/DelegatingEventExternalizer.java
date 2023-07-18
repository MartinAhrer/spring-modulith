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

import java.util.function.BiConsumer;

import org.springframework.modulith.ApplicationModuleListener;
import org.springframework.modulith.events.EventSerializer;
import org.springframework.modulith.events.externalize.EventExternalizationConfiguration;
import org.springframework.modulith.events.externalize.EventExternalizationConfiguration.RoutingTarget;
import org.springframework.stereotype.Component;

/**
 * @author Oliver Drotbohm
 */
@Component
public class DelegatingEventExternalizer extends AbstractEventExternalizer {

	private final BiConsumer<RoutingTarget, Object> delegate;

	/**
	 * @param serializer
	 * @param configuration
	 * @param delegate
	 */
	public DelegatingEventExternalizer(EventSerializer serializer, EventExternalizationConfiguration configuration,
			BiConsumer<RoutingTarget, Object> delegate) {
		super(serializer, configuration);
		this.delegate = delegate;
	}

	/*
	* (non-Javadoc)
	* @see org.springframework.modulith.events.foo.AbstractEventExternalizer#externalize(java.lang.Object)
	*/
	@Override
	@ApplicationModuleListener
	public void externalize(Object event) {
		super.externalize(event);
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.modulith.events.externalize.AbstractEventExternalizer#externalize(org.springframework.modulith.events.externalize.EventExternalizationConfiguration.RoutingTarget, java.lang.Object)
	 */
	@Override
	protected void externalize(RoutingTarget target, Object payload) {
		delegate.accept(target, payload);
	}
}
