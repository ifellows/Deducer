\name{rocplot}
\alias{rocplot}
\title{
 ROC Plot for a logistic regression model
}
\description{
  Plots the ROC Curve
}
\usage{
rocplot(logistic.model,diag=TRUE,pred.prob.labels=FALSE,prob.label.digits=3,AUC=TRUE)
}
\arguments{
  \item{logistic.model}{a glm object with binomial link function.}
  \item{diag}{a logical value indicating whether a diagonal reference line should be displayed.}
  \item{pred.prob.labels}{a logical value indicating whether the 
  predictive probabilities should be displayed}
  \item{prob.label.digits}{The number of digits of the predictive probabilities to be displayed.}
  \item{AUC}{a logical value indicating whether the estimated area under the curve should be displayed}
}
\author{
Ian Fellows adapted from the lroc function by Virasakdi Chongsuvivatwong
}
\value{
  a ggplot object
}
\examples{
model.glm <- glm(formula=income>5930.5 ~ education + women + type,
	family=binomial(),data=Prestige,na.action=na.omit)
rocplot(model.glm)
}
