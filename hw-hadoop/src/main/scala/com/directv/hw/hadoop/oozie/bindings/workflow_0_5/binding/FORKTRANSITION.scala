// JAXB

package com.directv.hw.hadoop.oozie.bindings.workflow_0_5.binding

import javax.xml.bind.annotation.{XmlAccessType, XmlAccessorType, XmlAttribute, XmlType}

import scala.beans.BeanProperty


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FORK_TRANSITION", namespace = "uri:oozie:workflow:0.5") class FORKTRANSITION {
  @XmlAttribute(name = "start", required = true)
  @BeanProperty var start: String = null

}
