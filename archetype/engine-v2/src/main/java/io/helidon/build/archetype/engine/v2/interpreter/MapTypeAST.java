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

package io.helidon.build.archetype.engine.v2.interpreter;

import java.util.LinkedList;

import io.helidon.build.archetype.engine.v2.descriptor.MapType;

/**
 * Archetype map AST node without key used in {@link ListTypeAST}.
 */
public class MapTypeAST extends ASTNode implements ConditionalNode {

    private int order = 100;

    MapTypeAST(int order, String currentDirectory) {
        super(currentDirectory);
        this.order = order;
    }

    /**
     * Get the list order.
     *
     * @return order
     */
    public int order() {
        return order;
    }

    @Override
    public <A> void accept(Visitor<A> visitor, A arg) {
        visitor.visit(this, arg);
    }

    static MapTypeAST from(MapType map, String currentDirectory) {
        MapTypeAST result = new MapTypeAST(map.order(), currentDirectory);

        LinkedList<Visitable> children = getChildren(map, currentDirectory);
        ConditionalNode.addChildren(map, result, children, currentDirectory);

        return result;
    }

    private static LinkedList<Visitable> getChildren(MapType map, String currentDirectory) {
        LinkedList<Visitable> result = new LinkedList<>();
        result.addAll(map.keyValues());
        result.addAll(transformList(map.keyLists(), l -> ModelKeyListAST.from(l, currentDirectory)));
        result.addAll(transformList(map.keyMaps(), m -> ModelKeyMapAST.from(m, currentDirectory)));
        return result;
    }
}