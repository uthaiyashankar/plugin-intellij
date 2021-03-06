/*
 *  Copyright (c) 2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.ballerinalang.plugins.idea.psi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementResolveResult;
import com.intellij.psi.ResolveResult;
import org.ballerinalang.plugins.idea.psi.impl.BallerinaPsiImplUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class FunctionReference extends BallerinaElementReference {

    public FunctionReference(@NotNull IdentifierPSINode element) {
        super(element);
    }

    @Override
    public boolean isDefinitionNode(PsiElement def) {
        return def instanceof FunctionDefinitionNode || def instanceof ConnectorDefinitionNode
                || def instanceof CallableUnitNameNode || def instanceof SimpleTypeNode
                || def instanceof FunctionNode;
    }

    @NotNull
    @Override
    public Object[] getVariants() {
        return new Object[]{"function1", "function2"};
    }

    @NotNull
    @Override
    public ResolveResult[] multiResolve(boolean incompleteCode) {
        //Todo: Use java8
        List<PsiElement> functions = BallerinaPsiImplUtil.resolveFunction(getElement());
        List<ResolveResult> results = new ArrayList<>();
        for (PsiElement function : functions) {
            results.add(new PsiElementResolveResult(function));
        }
        List<PsiElement> connectors = BallerinaPsiImplUtil.resolveConnector(getElement());
        for (PsiElement function : connectors) {
            results.add(new PsiElementResolveResult(function));
        }
        return results.toArray(new ResolveResult[results.size()]);
    }
}
