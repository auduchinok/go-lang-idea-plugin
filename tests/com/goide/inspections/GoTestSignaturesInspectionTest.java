/*
 * Copyright 2013-2015 Sergey Ignatov, Alexander Zolotov, Mihai Toader, Florin Patan
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

package com.goide.inspections;

import com.goide.codeInsight.imports.GoCodeInsightSettings;
import com.goide.quickfix.GoQuickFixTestBase;
import com.intellij.testFramework.LightProjectDescriptor;

public class GoTestSignaturesInspectionTest extends GoQuickFixTestBase {
  @Override
  protected void tearDown() throws Exception {
    GoCodeInsightSettings.getInstance().setOptimizeImportsOnTheFly(true);
    super.tearDown();
  }

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    setUpProjectSdk();
    myFixture.enableInspections(GoTestSignaturesInspection.class);
    GoCodeInsightSettings.getInstance().setOptimizeImportsOnTheFly(false);
  }

  @Override
  protected LightProjectDescriptor getProjectDescriptor() {
    return createMockProjectDescriptor();
  }

  @Override
  protected String getBasePath() {
    return "inspections/test-signatures";
  }

  protected void doTest() {
    String testName = getTestName(true);
    myFixture.configureByFile(testName + "_test.go");
    myFixture.checkHighlighting();
    applySingleQuickFix("Fix signature");
    myFixture.checkResultByFile(testName + "_test-after.go");
  }

  public void testExampleNonEmptySignature(){ doTest(); }
  public void testTestNoTestingImport()     { doTest(); }
  public void testTestWrongTestingAlias()   { doTest(); }
  public void testTestLocalTestingImport()  { doTest(); }
  public void testTestMain()                { doTest(); }
  public void testBenchmark()               { doTest(); }
}
