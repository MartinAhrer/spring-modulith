/*
 * Copyright 2022-2023 the original author or authors.
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
package example.order;

import example.catalog.spi.CatalogSpiInterface;
import example.inventory.InventoryManagementPublic;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Oliver Drotbohm
 */
@Service
@RequiredArgsConstructor
public class OrderManagement {

	// WORKSHOP:
	// <1> add this reference to the catalog module
	// is prohibited without declaring as NamedInterface
	// <2> to allow this reference
	//private final @NonNull CatalogSpiInterface catalog;

	// WORKSHOP:
	// <3> adding this creates a circular reference
    // because the inventory module has a reference to the order module
	// info: references a public class that the compiler would allow
	//private final @NonNull InventoryManagementPublic inventory;

	private final @NonNull ApplicationEventPublisher events;

	@Transactional
	public void complete() {
		events.publishEvent(new OrderCompleted(UUID.randomUUID()));
	}
}
