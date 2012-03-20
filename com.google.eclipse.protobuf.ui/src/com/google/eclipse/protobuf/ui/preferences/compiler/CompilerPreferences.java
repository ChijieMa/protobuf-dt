/*
 * Copyright (c) 2011 Google Inc.
 *
 * All rights reserved. This program and the accompanying materials are made available under the terms of the Eclipse
 * License v1.0 which accompanies this distribution, and is available at
 *
 * http://www.eclipse.org/legal/epl-v10.html
 */
package com.google.eclipse.protobuf.ui.preferences.compiler;

import static com.google.eclipse.protobuf.ui.preferences.compiler.PreferenceNames.*;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.xtext.ui.editor.preferences.*;

/**
 * @author alruiz@google.com (Alex Ruiz)
 */
public class CompilerPreferences {
  public static CompilerPreferences compilerPreferences(IPreferenceStoreAccess storeAccess, IProject project) {
    IPreferenceStore store = storeAccess.getWritablePreferenceStore(project);
    boolean enableProjectSettings = store.getBoolean(ENABLE_PROJECT_SETTINGS_PREFERENCE_NAME);
    if (!enableProjectSettings) {
      store = storeAccess.getWritablePreferenceStore();
    }
    return new CompilerPreferences(store);
  }

  private final IPreferenceStore store;
  private final CodeGenerationPreference java;
  private final CodeGenerationPreference cpp;
  private final CodeGenerationPreference python;

  private CompilerPreferences(IPreferenceStore store) {
    this.store = store;
    java = new CodeGenerationPreference(store, JAVA_CODE_GENERATION_ENABLED, JAVA_OUTPUT_DIRECTORY);
    cpp = new CodeGenerationPreference(store, CPP_CODE_GENERATION_ENABLED, CPP_OUTPUT_DIRECTORY);
    python = new CodeGenerationPreference(store, PYTHON_CODE_GENERATION_ENABLED, PYTHON_OUTPUT_DIRECTORY);
  }

  public boolean shouldCompileProtoFiles() {
    return store.getBoolean(COMPILE_PROTO_FILES);
  }

  public String protocPath() {
    return (useProtocInSystemPath()) ? "protoc" : store.getString(PROTOC_FILE_PATH);
  }

  private boolean useProtocInSystemPath() {
    return store.getBoolean(USE_PROTOC_IN_SYSTEM_PATH);
  }

  public String descriptorPath() {
    return store.getString(DESCRIPTOR_FILE_PATH);
  }

  public CodeGenerationPreference javaCodeGeneration() {
    return java;
  }

  public CodeGenerationPreference cppCodeGeneration() {
    return cpp;
  }

  public CodeGenerationPreference pythonCodeGeneration() {
    return python;
  }

  public boolean refreshResources() {
    return store.getBoolean(REFRESH_RESOURCES);
  }

  public boolean refreshProject() {
    return store.getBoolean(REFRESH_PROJECT);
  }

  public static class Initializer implements IPreferenceStoreInitializer {
    private static final String DEFAULT_OUTPUT_DIRECTORY = "src-gen";

    @Override public void initialize(IPreferenceStoreAccess storeAccess) {
      IPreferenceStore store = storeAccess.getWritablePreferenceStore();
      store.setDefault(ENABLE_PROJECT_SETTINGS_PREFERENCE_NAME, false);
      store.setDefault(USE_PROTOC_IN_SYSTEM_PATH, true);
      store.setDefault(USE_PROTOC_IN_CUSTOM_PATH, false);
      store.setDefault(JAVA_CODE_GENERATION_ENABLED, false);
      store.setDefault(CPP_CODE_GENERATION_ENABLED, false);
      store.setDefault(PYTHON_CODE_GENERATION_ENABLED, false);
      store.setDefault(JAVA_OUTPUT_DIRECTORY, DEFAULT_OUTPUT_DIRECTORY);
      store.setDefault(CPP_OUTPUT_DIRECTORY, DEFAULT_OUTPUT_DIRECTORY);
      store.setDefault(PYTHON_OUTPUT_DIRECTORY, DEFAULT_OUTPUT_DIRECTORY);
      store.setDefault(REFRESH_RESOURCES, true);
      store.setDefault(REFRESH_PROJECT, true);
      store.setDefault(REFRESH_OUTPUT_DIRECTORY, false);
    }
 }
}