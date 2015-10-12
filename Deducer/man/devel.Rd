\name{devel}
\alias{addComponent}
\alias{getSize}
\alias{setSize}
\alias{execute}
\alias{ButtonGroupWidget}
\alias{CheckBoxesWidget}
\alias{DeducerMain}
\alias{JLabel}
\alias{RDialogMonitor}
\alias{RDialog}
\alias{SimpleRDialog}
\alias{SimpleRSubDialog}
\alias{SingleVariableWidget}
\alias{SliderWidget}
\alias{TextAreaWidget}
\alias{VariableListWidget}
\alias{VariableSelectorWidget}
\alias{AddRemoveButtons}
\alias{ListWidget}
\alias{ComboBoxWidget}
\alias{TextFieldWidget}
\alias{ObjectChooserWidget}
\title{
  Deducer's plug-in development tools
}
\description{
  functions pertaining to GUI development
}
\usage{
addComponent(container, component, top, right, bottom, 
	left, topType = "REL", rightType = "REL", bottomType = "REL", 
	leftType = "REL")
getSize(component)
setSize(component,width,height)
execute(cmd)

ButtonGroupWidget 
CheckBoxesWidget 
DeducerMain 
JLabel 
RDialog
SimpleRDialog
SimpleRSubDialog 
SingleVariableWidget 
SliderWidget 
TextAreaWidget
VariableListWidget 
VariableSelectorWidget
ComboBoxWidget
RDialogMonitor
ListWidget
AddRemoveButtons
TextFieldWidget
ObjectChooserWidget
}
\arguments{
  \item{container}{A Java Swing container with Anchor layout}
  \item{component}{a Java Swing component }
  \item{top}{location of top of component 0 - 1000}
  \item{right}{location of right of component 0 - 1000}
  \item{bottom}{location of bottom of component 0 - 1000}
  \item{left}{location of left of component 0 - 1000}
  \item{topType}{Type of constraint on top of component. Can be "REL", "ABS", or "NONE"}
  \item{rightType}{Type of constraint on right of component. Can be "REL", "ABS", or "NONE"}
  \item{bottomType}{Type of constraint on bottom of component. Can be "REL", "ABS", or "NONE"}
  \item{leftType}{Type of constraint on left of component. Can be "REL", "ABS", or "NONE"}
  \item{height}{new height of component or window in pixels }
  \item{width}{new width of component or window in pixels }
  \item{cmd}{the command to be executed}
}
\details{
addComponent adds a Java object of class Component to a container 
(usually an RDialog or SimpleRDialog). the location of the component is
determined by the top, right, bottom, and left arguments, which are numbers
between 1 and 1000 indicating the distance from either the top (or left) of the
container, with 1000 indicating the opposite side of the container. Each side
can be constrained in three different ways. If the Type is "REL", the side will
scale proportional to the container when the container is resized. If it is
"ABS", it is not rescaled. If it is "NONE", the location of that side is determined
by the componet's preferred size, which can be set with the "setPreferedSize" method.

getSize gets the height and width

setSize sets the height and width

execute executes a character representing a command, as if it were entered into the console

The rest of the items are references to the Java classes of commonly used GUI components.
see www.deducer.org for more details and usage.

}