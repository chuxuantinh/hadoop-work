package com.directv.hw.hadoop.oozie.bindings.workflow_0_5

import java.io.InputStream
import java.lang.reflect.Type

import com.directv.hw.core.exception.DapException
import com.directv.hw.hadoop.oozie.bindings.workflow_0_5.WorkflowSerializerCommon._
import com.directv.hw.hadoop.oozie.bindings.workflow_0_5.binding._
import com.directv.hw.hadoop.oozie.service.WorkflowConverter.Oozie
import com.google.gson
import com.google.gson._
import resource._

import scala.collection.JavaConversions._
import scala.language.{implicitConversions, reflectiveCalls}

object pluginDescription {
  val jsonSchemaPath = "oozie/metadata/workflow_0_5/jsonSchema.json"

  lazy val additionalSerializers = List[(Class[_], Class[_])] (
    (classOf[DELETE], classOf[DeleteSerializer]),
    (classOf[MKDIR], classOf[MkdirSerializer]),
    (classOf[MOVE], classOf[MoveSerializer]),
    (classOf[CHMOD], classOf[ChmodSerializer]),
    (classOf[TOUCHZ], classOf[TouchzSerializer]),
    (classOf[CHGRP], classOf[ChgrpSerializer]),
    (classOf[FLAG], classOf[WorkflowFlagSerializer]),
    (classOf[PIG], classOf[PigSerializer]),
    (classOf[SUBWORKFLOW], classOf[SubworkflowSerializer]),
    (classOf[MAPREDUCE], classOf[MapReduceSerializer]),
    (classOf[JAVA], classOf[JavaSerializer]),
    (classOf[FS], classOf[FsSerializer])
  )

  lazy val additionalDeserializers = List[(Class[_], Class[_])] (
    (classOf[DELETE], classOf[DeleteDeserializer]),
    (classOf[MKDIR], classOf[MkdirDeserializer]),
    (classOf[MOVE], classOf[MoveDeserializer]),
    (classOf[CHMOD], classOf[ChmodDeserializer]),
    (classOf[TOUCHZ], classOf[TouchzDeserializer]),
    (classOf[CHGRP], classOf[ChgrpDeserializer]),
    (classOf[SUBWORKFLOW], classOf[SubworkflowDeserializer]),
    (classOf[FLAG], classOf[WorkflowFlagDeserializer]),
    (classOf[MAPREDUCE], classOf[MapReduceDeserializer]),
    (classOf[JAVA], classOf[JavaDeserializer]),
    (classOf[FS], classOf[FsDeserializer]),
    (classOf[PIG], classOf[PigDeserializer]),
    (classOf[ACTION], classOf[ActionDeserializer])
  )

  lazy val additionalActions = List[JsonElement] (
    com.directv.hw.hadoop.oozie.bindings.distcp_action_0_1.pluginDescription.getJsonSchema,
    com.directv.hw.hadoop.oozie.bindings.distcp_action_0_2.pluginDescription.getJsonSchema,
    com.directv.hw.hadoop.oozie.bindings.email_action_0_1.pluginDescription.getJsonSchema,
    com.directv.hw.hadoop.oozie.bindings.email_action_0_2.pluginDescription.getJsonSchema,
    com.directv.hw.hadoop.oozie.bindings.hive_action_0_2.pluginDescription.getJsonSchema,
    com.directv.hw.hadoop.oozie.bindings.hive_action_0_3.pluginDescription.getJsonSchema,
    com.directv.hw.hadoop.oozie.bindings.hive_action_0_4.pluginDescription.getJsonSchema,
    com.directv.hw.hadoop.oozie.bindings.hive_action_0_5.pluginDescription.getJsonSchema,
    com.directv.hw.hadoop.oozie.bindings.spark_action_0_1.pluginDescription.getJsonSchema,
    com.directv.hw.hadoop.oozie.bindings.spark_action_0_2.pluginDescription.getJsonSchema,
    com.directv.hw.hadoop.oozie.bindings.shell_action_0_1.pluginDescription.getJsonSchema,
    com.directv.hw.hadoop.oozie.bindings.shell_action_0_2.pluginDescription.getJsonSchema,
    com.directv.hw.hadoop.oozie.bindings.shell_action_0_3.pluginDescription.getJsonSchema,
    com.directv.hw.hadoop.oozie.bindings.sqoop_action_0_2.pluginDescription.getJsonSchema,
    com.directv.hw.hadoop.oozie.bindings.sqoop_action_0_3.pluginDescription.getJsonSchema,
    com.directv.hw.hadoop.oozie.bindings.sqoop_action_0_4.pluginDescription.getJsonSchema,
    com.directv.hw.hadoop.oozie.bindings.ssh_action_0_1.pluginDescription.getJsonSchema,
    com.directv.hw.hadoop.oozie.bindings.ssh_action_0_2.pluginDescription.getJsonSchema
  )

  def getJsonSchema: JsonElement = {
    val jsonSchemaString = resourceAsString(jsonSchemaPath)
    val result = new JsonParser().parse(jsonSchemaString).getAsJsonObject
    val actions = result.getAsJsonObject("subtypes").getAsJsonArray("action")
    additionalActions.foreach {
      additionalAction =>
        actions.add(additionalAction)
    }
    result
  }

  def getJsonSchemaAsString: String = getJsonSchema.toString

  private def resourceAsString(path: String) = {
    managed(getClass.getClassLoader.getResourceAsStream(path)).map { is: InputStream =>
      scala.io.Source.fromInputStream(is).getLines().mkString("\n")
    }.opt.getOrElse(throw new DapException(s"Could not load resource [$path]"))
  }

  class extJsonHas(val json: JsonObject) {
    def hasStringData(key: String) = json.has(key) && json.get(key).getAsString != ""
  }
  implicit def jsonObject2extJsonHas(json: JsonObject): extJsonHas = new extJsonHas(json)

}

import com.directv.hw.hadoop.oozie.bindings.workflow_0_5.pluginDescription.jsonObject2extJsonHas

class GlobalSerializer() extends JsonSerializer[GLOBAL] {
  override def serialize(global: GLOBAL, typeOfSrc: Type, context: JsonSerializationContext): JsonElement = {
    val result = new JsonObject
    result.addProperty("name-node", global.getNameNode)
    result.addProperty("job-tracker", global.getJobTracker)
    result.add("job-xml", context.serialize(global.getJobXml))
    addConfiguration(result, global, context)
    result
  }
}

class GlobalDeserializer() extends JsonDeserializer[GLOBAL] {
  @throws(classOf[gson.JsonParseException])
  override def deserialize(globalJson: JsonElement, typeOfT: Type, context: JsonDeserializationContext) = {
    val json = globalJson.getAsJsonObject
    val global = new GLOBAL
    if (json != null) {
      if (json.hasStringData("name-node")) global.setNameNode(json.get("name-node").getAsString)
      if (json.hasStringData("job-tracker")) global.setJobTracker(json.get("job-tracker").getAsString)
      if (json.has("job-xml")) json.get("job-xml").getAsJsonArray foreach { jobXml => global.getJobXml.add(jobXml.getAsString) }
      extractConfiguration(json, global, context)
    }
    global
  }
}

class DeleteSerializer() extends JsonSerializer[DELETE] {
  override def serialize(delete: DELETE, typeOfSrc: Type, context: JsonSerializationContext): JsonElement = {
    val innerDelete = new JsonObject
    innerDelete.addProperty("path", delete.getPath)
    innerDelete
  }
}

class DeleteDeserializer() extends JsonDeserializer[DELETE] {
  @throws (classOf[gson.JsonParseException])
  override def deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext)  = {
    import pluginDescription.jsonObject2extJsonHas
    val delete = new DELETE
    val wrapper = json.getAsJsonObject
    if (wrapper != null) {
      if (wrapper.hasStringData("path")) delete.setPath(wrapper.get("path").getAsString)
    }
    delete
  }
}

class MkdirSerializer() extends JsonSerializer[MKDIR] {
  override def serialize(mkdir: MKDIR, typeOfSrc: Type, context: JsonSerializationContext): JsonElement = {
    val innerMkdir = new JsonObject
    innerMkdir.addProperty("path", mkdir.getPath)
    innerMkdir
  }
}
class MkdirDeserializer() extends JsonDeserializer[MKDIR] {
  @throws (classOf[gson.JsonParseException])
  override def deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext)  = {
    val mkdir = new MKDIR
    val wrapper = json.getAsJsonObject
    if (wrapper != null) {
      if (wrapper.hasStringData("path")) mkdir.setPath(wrapper.get("path").getAsString)
    }
    mkdir
  }
}



class MoveSerializer() extends JsonSerializer[MOVE] {
  override def serialize(move: MOVE, typeOfSrc: Type, context: JsonSerializationContext): JsonElement = {
    val innerMove = new JsonObject
    innerMove.addProperty("source", move.getSource)
    innerMove.addProperty("target", move.getTarget)
    innerMove
  }
}
class MoveDeserializer() extends JsonDeserializer[MOVE] {
  @throws (classOf[gson.JsonParseException])
  override def deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext)  = {
    val move = new MOVE
    val wrapper = json.getAsJsonObject
    if (wrapper != null) {
      if (wrapper.hasStringData("source")) move.setSource(wrapper.get("source").getAsString)
      if (wrapper.hasStringData("target")) move.setTarget(wrapper.get("target").getAsString)
    }
    move
  }
}

class ChmodSerializer() extends JsonSerializer[CHMOD] {
  override def serialize(chmod: CHMOD, typeOfSrc: Type, context: JsonSerializationContext): JsonElement = {
    val innerChmod = new JsonObject
    innerChmod.addProperty("recursive", chmod.getRecursive != null)
    innerChmod.addProperty("path", chmod.getPath)
    innerChmod.addProperty("permissions", chmod.getPermissions)
    innerChmod.addProperty("dir-files", chmod.getDirFiles)
    innerChmod
  }
}
class ChmodDeserializer() extends JsonDeserializer[CHMOD] {
  @throws (classOf[gson.JsonParseException])
  override def deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext)  = {
    val chmod = new CHMOD
    val wrapper = json.getAsJsonObject
    if (wrapper != null) {
      if (wrapper.has("recursive")) if (wrapper.get("recursive").getAsBoolean) chmod.setRecursive(new FLAG())
      if (wrapper.hasStringData("path")) chmod.setPath(wrapper.get("path").getAsString)
      if (wrapper.hasStringData("permissions")) chmod.setPermissions(wrapper.get("permissions").getAsString)
      if (wrapper.hasStringData("dir-files")) chmod.setDirFiles(wrapper.get("dir-files").getAsString)
    }
    chmod
  }
}

class TouchzSerializer() extends JsonSerializer[TOUCHZ] {
  override def serialize(touchz: TOUCHZ, typeOfSrc: Type, context: JsonSerializationContext): JsonElement = {
    val innerTouchz = new JsonObject
    innerTouchz.addProperty("path", touchz.getPath)
    innerTouchz
  }
}
class TouchzDeserializer() extends JsonDeserializer[TOUCHZ] {
  @throws (classOf[gson.JsonParseException])
  override def deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext)  = {
    val touchz = new TOUCHZ
    val wrapper = json.getAsJsonObject
    if (wrapper != null) {
      if (wrapper.hasStringData("path")) touchz.setPath(wrapper.get("path").getAsString)
    }
    touchz
  }
}



class ChgrpSerializer() extends JsonSerializer[CHGRP] {
  override def serialize(chgrp: CHGRP, typeOfSrc: Type, context: JsonSerializationContext): JsonElement = {
    val innerChgrp = new JsonObject
    innerChgrp.addProperty("recursive", chgrp.getRecursive != null)
    innerChgrp.addProperty("path", chgrp.getPath)
    innerChgrp.addProperty("group", chgrp.getGroup)
    innerChgrp.addProperty("dir-files", chgrp.getDirFiles)
    innerChgrp
  }
}
class ChgrpDeserializer() extends JsonDeserializer[CHGRP] {
  @throws (classOf[gson.JsonParseException])
  override def deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext)  = {
    val chgrp = new CHGRP
    val wrapper = json.getAsJsonObject
    if (wrapper != null) {
      if (wrapper.has("recursive")) if (wrapper.get("recursive").getAsBoolean) chgrp.setRecursive(new FLAG())
      if (wrapper.hasStringData("path")) chgrp.setPath(wrapper.get("path").getAsString)
      if (wrapper.hasStringData("group")) chgrp.setGroup(wrapper.get("group").getAsString)
      if (wrapper.hasStringData("dir-files")) chgrp.setDirFiles(wrapper.get("dir-files").getAsString)
    }
    chgrp
  }
}

class SubworkflowSerializer() extends JsonSerializer[SUBWORKFLOW] {
  override def serialize(subworkflow: SUBWORKFLOW, typeOfSrc: Type, context: JsonSerializationContext): JsonElement = {
    val result = new JsonObject
    result.addProperty("app-path", subworkflow.getAppPath)
    result.addProperty("propagate-configuration", subworkflow.getPropagateConfiguration != null)
    addConfiguration(result, subworkflow, context)
    result
  }
}
class SubworkflowDeserializer() extends JsonDeserializer[SUBWORKFLOW] {
  @throws (classOf[gson.JsonParseException])
  override def deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext)  = {
    val subworkflow = new SUBWORKFLOW
    val subworkflowJson = json.getAsJsonObject
    if (subworkflowJson != null) {
      if (subworkflowJson.has("propagate-configuration") && subworkflowJson.get("propagate-configuration").getAsBoolean) subworkflow
        .setPropagateConfiguration(new FLAG())
      if (subworkflowJson.hasStringData("app-path")) subworkflow.setAppPath(subworkflowJson.get("app-path").getAsString)
      extractConfiguration(subworkflowJson, subworkflow, context)
    }
    subworkflow
  }
}

class WorkflowFlagSerializer() extends JsonSerializer[FLAG] {
  override def serialize(flag: FLAG, typeOfSrc: Type, context: JsonSerializationContext): JsonElement = {
    new JsonPrimitive(flag != null)
  }
}

class WorkflowFlagDeserializer() extends JsonDeserializer[FLAG] {
  @throws (classOf[gson.JsonParseException])
  override def deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext)  = {
    if (json.getAsBoolean) new FLAG else null
  }
}


class MapReduceSerializer() extends JsonSerializer[MAPREDUCE] {
  import Oozie.mapreduce._

  override def serialize(mapReduce: MAPREDUCE, typeOfSrc: Type, context: JsonSerializationContext): JsonElement = {
    val result = new JsonObject
    result.addProperty("job-tracker", mapReduce.getJobTracker)
    result.addProperty("name-node", mapReduce.getNameNode)
    result.add("prepare", context.serialize(mapReduce.getPrepare, classOf[PREPARE]))
    val streamingOrPipes = new JsonArray
    if (mapReduce.getStreaming != null) {
      val streaming = new JsonObject
      streaming.add("streaming", context.serialize(mapReduce.getStreaming, classOf[STREAMING]))
      streamingOrPipes.add(streaming)
    }
    if (mapReduce.getPipes != null) {
      val pipes = new JsonObject
      pipes.add("pipes", context.serialize(mapReduce.getPipes, classOf[PIPES]))
      streamingOrPipes.add(pipes)
    }
    result.add("streamingOrPipes", streamingOrPipes)
    result.add("job-xml", context.serialize(mapReduce.getJobXml))
    if (isMapReduce2(mapReduce)) {
      addConfiguration(result, mapReduce, context, commonKeys ++ v2Keys, versionSelectKeys.toList)
    } else {
      addConfiguration(result, mapReduce, context, commonKeys ++ v1Keys)
    }
    result.add("file", context.serialize(mapReduce.getFile))
    result.add("archive", context.serialize(mapReduce.getArchive))
    result
  }
  
  // TODO (vkolischuk) code duplicated in WorkflowToGraphConverterImpl
  private def isMapReduce2(mapReduce: MAPREDUCE) = {
    Option(mapReduce.configuration) map { configuration =>
      configuration.getProperty filter { property =>
        Oozie.mapreduce.versionSelectKeys.contains(property.getName) && Oozie.mapreduce.versionSelectValue == property.getValue
      } groupBy(_.getName)
    } exists (_.size > 1)
  }
}

class MapReduceDeserializer() extends JsonDeserializer[MAPREDUCE] {
  import Oozie.mapreduce._

  @throws(classOf[gson.JsonParseException])
  override def deserialize(mapreduceJson: JsonElement, typeOfT: Type, context: JsonDeserializationContext) = {
    val json = mapreduceJson.getAsJsonObject
    val mapReduce = new MAPREDUCE
    if (json != null) {
      if (json.hasStringData("job-tracker")) mapReduce.setJobTracker(json.get("job-tracker").getAsString)
      if (json.hasStringData("name-node")) mapReduce.setNameNode(json.get("name-node").getAsString)
      if (json.has("prepare")) mapReduce.setPrepare(context.deserialize(json.get("prepare").getAsJsonObject, classOf[PREPARE]))
      if (mapReduce.getPrepare.getDelete.isEmpty && mapReduce.getPrepare.getMkdir.isEmpty) mapReduce.setPrepare(null)
      val choiceArray = json.getAsJsonArray("streamingOrPipes")
      if (choiceArray != null) {
        choiceArray.foreach { choice ⇒
          val streamingOrPipes = choice.getAsJsonObject
          if (streamingOrPipes.has("streaming"))
            mapReduce.setStreaming(context.deserialize(streamingOrPipes.get("streaming").getAsJsonObject, classOf[STREAMING]))
          if (streamingOrPipes.has("pipes"))
            mapReduce.setPipes(context.deserialize(streamingOrPipes.get("pipes").getAsJsonObject, classOf[PIPES]))
        }
      }
      if (json.has("job-xml")) json.get("job-xml").getAsJsonArray.foreach { jobXml => mapReduce.getJobXml.add(jobXml.getAsString) }
      (commonKeys ++ versionSelectKeys.toList ++ v1Keys ++ v2Keys) foreach { key =>
        extractPropertyToConfiguration(json, mapReduce, key)
      }
      extractConfiguration(json, mapReduce, context)
      if (json.has("file")) json.get("file").getAsJsonArray foreach { file => mapReduce.getFile.add(file.getAsString) }
      if (json.has("archive")) json.get("archive").getAsJsonArray foreach { archive => mapReduce.getArchive.add(archive.getAsString) }
    }
    mapReduce
  }
}

class JavaSerializer() extends JsonSerializer[JAVA] {
  override def serialize(`java`: JAVA, typeOfSrc: Type, context: JsonSerializationContext): JsonElement = {
    val result = new JsonObject
    result.addProperty("job-tracker", `java`.getJobTracker)
    result.addProperty("name-node", `java`.getNameNode)
    result.add("prepare", context.serialize(`java`.getPrepare, classOf[PREPARE]))
    result.add("job-xml", context.serialize(`java`.getJobXml))
    addConfiguration(result, java, context)
    result.addProperty("main-class", `java`.getMainClass)

    val javaOptsOrJavaOpt = new JsonArray
    if (`java`.getJavaOpts != null) {
      val javaOpts = new JsonObject
      javaOpts.addProperty("java-opts", `java`.getJavaOpts)
      javaOptsOrJavaOpt.add(javaOpts)
    }
    if (`java`.getJavaOpt != null) {
      val javaOpt = new JsonObject
      javaOpt.add("java-opt", context.serialize(`java`.getJavaOpt))
      javaOptsOrJavaOpt.add(javaOpt)
    }
    result.add("javaOptsOrJavaOpt", javaOptsOrJavaOpt)
    result.add("arg", context.serialize(`java`.getArg))
    result.add("file", context.serialize(`java`.getFile))
    result.add("archive", context.serialize(`java`.getArchive))
    result.addProperty("capture-output", `java`.getCaptureOutput != null)
    result
  }
}

class JavaDeserializer() extends JsonDeserializer[JAVA] {
  @throws(classOf[gson.JsonParseException])
  override def deserialize(javaJson: JsonElement, typeOfT: Type, context: JsonDeserializationContext) = {
    val json = javaJson.getAsJsonObject
    val java = new JAVA
    if (json != null) {
      if (json.hasStringData("job-tracker")) java.setJobTracker(json.get("job-tracker").getAsString)
      if (json.hasStringData("name-node")) java.setNameNode(json.get("name-node").getAsString)
      if (json.has("prepare")) {
        java.setPrepare(context.deserialize(json.get("prepare").getAsJsonObject, classOf[PREPARE]))
        if (java.getPrepare.getDelete.isEmpty && java.getPrepare.getMkdir.isEmpty) java.setPrepare(null)
      }
      if (json.has("job-xml")) json.get("job-xml").getAsJsonArray foreach { jobXml => java.getJobXml.add(jobXml.getAsString) }
      extractConfiguration(json, java, context)

      if (json.has("javaOptsOrJavaOpt")) json.getAsJsonArray("javaOptsOrJavaOpt") foreach { choice ⇒
        val javaOptsOrJavaOpt = choice.getAsJsonObject
        if (javaOptsOrJavaOpt.has("java-opts")) java.setJavaOpts(javaOptsOrJavaOpt.get("java-opts").getAsString)
        if (javaOptsOrJavaOpt.has("java-opt")) javaOptsOrJavaOpt.get("java-opt").getAsJsonArray foreach (javaOpt => java.getJavaOpt.add(javaOpt
          .getAsString))
      }

      if (json.hasStringData("main-class")) java.setMainClass(json.get("main-class").getAsString)
      if (json.has("arg")) json.get("arg").getAsJsonArray foreach { arg => java.getArg.add(arg.getAsString) }
      if (json.has("file")) json.get("file").getAsJsonArray foreach { file => java.getFile.add(file.getAsString) }
      if (json.has("archive")) json.get("archive").getAsJsonArray foreach { archive => java.getArchive.add(archive.getAsString) }
      if (json.has("capture-output") && json.get("capture-output").getAsBoolean) java.setCaptureOutput(new FLAG)
    }
    java
  }
}

class FsSerializer() extends JsonSerializer[FS] {
  override def serialize(fs: FS, typeOfSrc: Type, context: JsonSerializationContext): JsonElement = {
    val inners = List(
      ("delete", classOf[DELETE]),
      ("mkdir", classOf[MKDIR]),
      ("move", classOf[MOVE]),
      ("chmod", classOf[CHMOD]),
      ("touchz", classOf[TOUCHZ]),
      ("chgrp", classOf[CHGRP])
    )
    val result = new JsonObject
    result.addProperty("name-node", fs.getNameNode)
    result.add("job-xml", context.serialize(fs.getJobXml))
    addConfiguration(result, fs, context)
    val deleteOrMkdirOrMove = new JsonArray
    fs.getDeleteOrMkdirOrMove.foreach { innerObject ⇒
      val innerJson = new JsonObject
      val descr = inners.find(_._2 == innerObject.getClass).get
      innerJson.add(descr._1, context.serialize(innerObject, descr._2))
      deleteOrMkdirOrMove.add(innerJson)
    }
    result.add("deleteOrMkdirOrMove", deleteOrMkdirOrMove)
    result
  }
}


class FsDeserializer() extends JsonDeserializer[FS] {
  @throws(classOf[gson.JsonParseException])
  override def deserialize(fsJson: JsonElement, typeOfT: Type, context: JsonDeserializationContext) = {
    import pluginDescription.jsonObject2extJsonHas
    val json = fsJson.getAsJsonObject
    val fs = new FS
    if (json != null) {
      if (json.hasStringData("name-node")) fs.setNameNode(json.get("name-node").getAsString)
      if (json.has("job-xml")) json.get("job-xml").getAsJsonArray foreach { jobXml => fs.getJobXml.add(jobXml.getAsString) }
      extractConfiguration(json, fs, context)
      if (json.has("deleteOrMkdirOrMove")) json.get("deleteOrMkdirOrMove").getAsJsonArray foreach {
        deleteOrMkDir =>
          List(("delete", classOf[DELETE]),
            ("mkdir", classOf[MKDIR]),
            ("move", classOf[MOVE]),
            ("chmod", classOf[CHMOD]),
            ("touchz", classOf[TOUCHZ]),
            ("chgrp", classOf[CHGRP])
          ) foreach { clazzTuple =>
            if (deleteOrMkDir.getAsJsonObject.has(clazzTuple._1))
              fs.getDeleteOrMkdirOrMove.add(context.deserialize(deleteOrMkDir.getAsJsonObject.get(clazzTuple._1), clazzTuple._2))
          }
      }
      if (fs.getDeleteOrMkdirOrMove.isEmpty) fs.setDeleteOrMkdirOrMove(null)
    }
    fs
  }
}

class PigSerializer() extends JsonSerializer[PIG] {
  @throws(classOf[gson.JsonParseException])
  override def serialize(pig: PIG, typeOfSrc: Type, context: JsonSerializationContext): JsonElement = {
    val result = new JsonObject
    result.addProperty("job-tracker", pig.getJobTracker)
    result.addProperty("name-node", pig.getNameNode)
    result.add("prepare", context.serialize(pig.getPrepare, classOf[PREPARE]))
    if (pig.getScript != null) {
      result.add("script", context.serialize(pig.getScript))
    }
    result.add("job-xml", context.serialize(pig.getJobXml))
    addConfiguration(result, pig, context)
    result.add("param", context.serialize(pig.getParam))
    result.add("argument", context.serialize(pig.getArgument))
    result.add("file", context.serialize(pig.getFile))
    result.add("archive", context.serialize(pig.getArchive))
    result
  }
}

class PigDeserializer() extends JsonDeserializer[PIG] {
  @throws(classOf[gson.JsonParseException])
  override def deserialize(pigJson: JsonElement, typeOfT: Type, context: JsonDeserializationContext) = {
    val json = pigJson.getAsJsonObject
    val pig = new PIG
    if (json != null) {
      if (json.hasStringData("job-tracker")) pig.setJobTracker(json.get("job-tracker").getAsString)
      if (json.hasStringData("name-node")) pig.setNameNode(json.get("name-node").getAsString)
      if (json.has("prepare")) {
        pig.setPrepare(context.deserialize(json.get("prepare").getAsJsonObject, classOf[PREPARE]))
        if (pig.getPrepare.getDelete.isEmpty && pig.getPrepare.getMkdir.isEmpty) pig.setPrepare(null)
      }
      if (json.has("job-xml")) json.get("job-xml").getAsJsonArray foreach { jobXml => pig.getJobXml.add(jobXml.getAsString) }
      extractConfiguration(json, pig, context)
      pig.setScript(json.get("script").getAsString)
      if (json.has("param")) json.get("param").getAsJsonArray foreach { param => pig.getParam.add(param.getAsString) }
      if (json.has("argument")) json.get("argument").getAsJsonArray foreach { argument => pig.getArgument.add(argument.getAsString) }
      if (json.has("file")) json.get("file").getAsJsonArray foreach { file => pig.getFile.add(file.getAsString) }
      if (json.has("archive")) json.get("archive").getAsJsonArray foreach { archive => pig.getArchive.add(archive.getAsString) }
    }
    pig
  }
}

class ActionDeserializer() extends JsonDeserializer[ACTION] {
  @throws(classOf[gson.JsonParseException])
  override def deserialize(actionJson: JsonElement, typeOfT: Type, context: JsonDeserializationContext) = {
    val json = actionJson.getAsJsonObject
    val action = new ACTION
    if (json != null) {
      if (json.has("map-reduce")) action.setMapReduce(context.deserialize(json.get("map-reduce"), classOf[MAPREDUCE]))
      if (json.has("map-reduce2")) {
        val jsonMapReduce2 = json.getAsJsonObject("map-reduce2")
        Oozie.mapreduce.versionSelectKeys foreach { key =>
          jsonMapReduce2.addProperty(key, Oozie.mapreduce.versionSelectValue)
        }
        action.setMapReduce(context.deserialize(jsonMapReduce2, classOf[MAPREDUCE]))
      }
      if (json.has("pig")) action.setPig(context.deserialize(json.get("pig"), classOf[PIG]))
      if (json.has("sub-workflow")) action.setSubWorkflow(context.deserialize(json.get("sub-workflow"), classOf[SUBWORKFLOW]))
      if (json.has("fs")) action.setFs(context.deserialize(json.get("fs"), classOf[FS]))
      if (json.has("java")) action.setJava(context.deserialize(json.get("java"), classOf[JAVA]))
      if (json.has("sqoop_0_4")) action.setSqoop_0_4(context.deserialize(json.get("sqoop_0_4"), classOf[com.directv.hw.hadoop.oozie.bindings.sqoop_action_0_4.binding.ACTION]))
      if (json.has("sqoop_0_3")) action.setSqoop_0_3(context.deserialize(json.get("sqoop_0_3"), classOf[com.directv.hw.hadoop.oozie.bindings.sqoop_action_0_3.binding.ACTION]))
      if (json.has("sqoop_0_2")) action.setSqoop_0_2(context.deserialize(json.get("sqoop_0_2"), classOf[com.directv.hw.hadoop.oozie.bindings.sqoop_action_0_2.binding.ACTION]))
      if (json.has("ssh_0_2")) action.setSsh_0_2(context.deserialize(json.get("ssh_0_2"), classOf[com.directv.hw.hadoop.oozie.bindings.ssh_action_0_2.binding.ACTION]))
      if (json.has("ssh_0_1")) action.setSsh_0_1(context.deserialize(json.get("ssh_0_1"), classOf[com.directv.hw.hadoop.oozie.bindings.ssh_action_0_1.binding.ACTION]))
      if (json.has("shell_0_3")) action.setShell_0_3(context.deserialize(json.get("shell_0_3"), classOf[com.directv.hw.hadoop.oozie.bindings.shell_action_0_3.binding.ACTION]))
      if (json.has("shell_0_2")) action.setShell_0_2(context.deserialize(json.get("shell_0_2"), classOf[com.directv.hw.hadoop.oozie.bindings.shell_action_0_2.binding.ACTION]))
      if (json.has("shell_0_1")) action.setShell_0_1(context.deserialize(json.get("shell_0_1"), classOf[com.directv.hw.hadoop.oozie.bindings.shell_action_0_1.binding.ACTION]))
      if (json.has("spark_0_1")) action.setSpark_0_1(context.deserialize(json.get("spark_0_1"), classOf[com.directv.hw.hadoop.oozie.bindings.spark_action_0_1.binding.ACTION]))
      if (json.has("spark_0_2")) action.setSpark_0_2(context.deserialize(json.get("spark_0_2"), classOf[com.directv.hw.hadoop.oozie.bindings.spark_action_0_2.binding.ACTION]))
      if (json.has("hive_0_5")) action.setHive_0_5(context.deserialize(json.get("hive_0_5"), classOf[com.directv.hw.hadoop.oozie.bindings.hive_action_0_5.binding.ACTION]))
      if (json.has("hive_0_4")) action.setHive_0_4(context.deserialize(json.get("hive_0_4"), classOf[com.directv.hw.hadoop.oozie.bindings.hive_action_0_4.binding.ACTION]))
      if (json.has("hive_0_3")) action.setHive_0_3(context.deserialize(json.get("hive_0_3"), classOf[com.directv.hw.hadoop.oozie.bindings.hive_action_0_3.binding.ACTION]))
      if (json.has("hive_0_2")) action.setHive_0_2(context.deserialize(json.get("hive_0_2"), classOf[com.directv.hw.hadoop.oozie.bindings.hive_action_0_2.binding.ACTION]))
      if (json.has("email_0_2")) action.setEmail_0_2(context.deserialize(json.get("email_0_2"), classOf[com.directv.hw.hadoop.oozie.bindings.email_action_0_2.binding.ACTION]))
      if (json.has("email_0_1")) action.setEmail_0_1(context.deserialize(json.get("email_0_1"), classOf[com.directv.hw.hadoop.oozie.bindings.email_action_0_1.binding.ACTION]))
      if (json.has("distcp_0_2")) action.setDistcp_0_2(context.deserialize(json.get("distcp_0_2"), classOf[com.directv.hw.hadoop.oozie.bindings.distcp_action_0_2.binding.ACTION]))
      if (json.has("distcp_0_1")) action.setDistcp_0_1(context.deserialize(json.get("distcp_0_1"), classOf[com.directv.hw.hadoop.oozie.bindings.distcp_action_0_1.binding.ACTION]))
      if (json.hasStringData("name")) action.setName(json.get("name").getAsString)
      if (json.hasStringData("cred")) action.setCred(json.get("cred").getAsString)
      if (json.hasStringData("retry-max")) action.setRetryMax(json.get("retry-max").getAsString)
      if (json.hasStringData("retry-interval")) action.setRetryInterval(json.get("retry-interval").getAsString)

    }
    action
  }
}

object WorkflowSerializerCommon {
  //noinspection AccessorLikeMethodIsEmptyParen
  type WithConfiguration = {
    def getConfiguration(): CONFIGURATION
    def setConfiguration(configuration: CONFIGURATION)
  }

  def addConfiguration(jsonObject: JsonObject, source: WithConfiguration, context: JsonSerializationContext,
                       topLevelKeys: List[String] = List.empty, ignoredKeys: List[String] = List.empty) = {
    Option(source.getConfiguration()) flatMap(c => Option(c.getProperty)) withFilter(_.nonEmpty) foreach { allProperties =>
      val properties = allProperties filterNot (p => ignoredKeys.contains(p.getName))
      val (topLevel, regular) = properties partition (p => topLevelKeys.contains(p.getName))
      topLevel foreach { property =>
        jsonObject.addProperty(property.getName, property.getValue)
      }

      val array = new JsonArray
      regular foreach { property =>
        array.add(context.serialize(property, classOf[CONFIGURATION.Property]))
      }
      jsonObject.add("property", array)
    }
  }

  def extractConfiguration(json: JsonObject, target: WithConfiguration, context: JsonDeserializationContext) = {
    if (json.has("property")) {
      val jsonArray: JsonArray = json.get("property").getAsJsonArray
      if (jsonArray.nonEmpty) {
        if (target.getConfiguration == null) target.setConfiguration(new CONFIGURATION)
        val configuration = target.getConfiguration()
        jsonArray foreach { property =>
          configuration.getProperty.add(context.deserialize(property.getAsJsonObject, classOf[CONFIGURATION.Property]))
        }
      }
    }
  }

  def extractPropertyToConfiguration(json: JsonObject, target: WithConfiguration, key: String) = {
    if (json.hasStringData(key)) {
      val value = json.get(key).getAsString
      if (target.getConfiguration == null) target.setConfiguration(new CONFIGURATION)
      val property = new CONFIGURATION.Property()
      property.setName(key)
      property.setValue(value)
      target.getConfiguration().getProperty.add(property)
    }
  }
}
