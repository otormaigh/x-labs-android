/*
 * Copyright 2018 Elliot Tormey
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

import freemarker.template.Configuration
import freemarker.template.TemplateExceptionHandler
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.options.Option
import java.io.File
import java.nio.charset.Charset

open class TemplateProcessor : DefaultTask() {
  @Option(option = "name",
      description = "The name of the module")
  lateinit var moduleName: String
  private val packageName by lazy { "ie.elliot.x.labs.${moduleName.replace("-", "")}" }

  private val templateDir by lazy { File(project.rootDir, "buildSrc/src/template/module/") }
  private val dataModel by lazy {
    mapOf(
        Pair("module", moduleName),
        Pair("packageName", packageName))
  }
  private val config by lazy {
    Configuration(Configuration.VERSION_2_3_23).apply {
      setDirectoryForTemplateLoading(templateDir)
      defaultEncoding = "UTF-8"
      templateExceptionHandler = TemplateExceptionHandler.RETHROW_HANDLER
    }
  }

  @TaskAction
  fun generateModule() {
    logger.info("Using template files in ${templateDir.absolutePath}")
    File("${project.rootDir}/settings.gradle").appendText(", ':$moduleName'", Charset.forName("UTF-8"))

    val moduleDir = project.mkdir(File(project.rootDir, moduleName))
    processTemplate(moduleDir, "build.gradle", "build.gradle.ftl")
    processTemplate(moduleDir, ".gitignore", "gitignore.ftl")
    processTemplate(moduleDir, "proguard-rules.pro", "proguard-rules.pro.ftl")
    processTemplate(moduleDir, "README.md", "README.md.ftl")

    val mainDir = project.mkdir(File(moduleDir, "src/main"))
    processTemplate(mainDir, "AndroidManifest.xml", "AndroidManifest.xml.ftl")
    project.copy {
      from("$templateDir/res")
      into("$mainDir/res")
    }

    val resDir = File("$mainDir/res")
    processTemplate(resDir, "values/strings.xml", "strings.xml.ftl")

    val javaDir = project.mkdir(File("$mainDir/java/${packageName.replace(".", "/")}"))
    processTemplate(javaDir, "MainActivity.kt", "MainActivity.kt.ftl")
    processTemplate(javaDir, "Application.kt", "Application.kt.ftl")
  }

  private fun processTemplate(outDir: File, outFileName: String, templateFileName: String) {
    File(outDir, outFileName).writer(Charset.forName("UTF-8")).use { out ->
      val templateName = File(templateDir, templateFileName)
      config.getTemplate(templateName.name).process(dataModel, out)
    }
  }
}