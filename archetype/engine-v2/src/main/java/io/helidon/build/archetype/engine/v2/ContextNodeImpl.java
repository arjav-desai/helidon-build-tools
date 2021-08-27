/*
 * Copyright (c) 2021 Oracle and/or its affiliates.
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
package io.helidon.build.archetype.engine.v2;

import java.util.List;
import java.util.Optional;

public class ContextNodeImpl implements ContextNode {

    private final List<ContextNode> nodes;
    private final String name;
    private ContextNode parent;

    ContextNodeImpl(String name, List<ContextNode> nodes) {
        this.name = name;
        this.nodes = nodes;
        if (nodes != null && !nodes.isEmpty()) {
            for (ContextNode node : nodes) {
                node.parent(this);
            }
        }
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public Optional<ContextValue> value() {
        return Optional.empty();
    }

    @Override
    public List<ContextNode> children() {
        return nodes;
    }

    @Override
    public ContextNode parent(){
        return parent;
    }

    @Override
    public void parent(ContextNode parent) {
        this.parent = parent;
    }
}
