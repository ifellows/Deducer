\name{onesample.plot}
\alias{onesample.plot}
\title{onesample.plot}
\description{
  plots for one sample tests
}
\usage{
onesample.plot(variables,data=NULL,test.value,scale=FALSE,type="hist",alpha=.2)
}
\arguments{
  \item{variables}{An expression denoting a set of variable.}
  \item{data}{A data.frame from which the variables will be selected.}
  \item{test.value}{null hypothesis test value}
  \item{scale}{scale variables}
  \item{type}{type of plot. 'hist' or 'box' are allowed}
  \item{alpha}{transparency of points for box plot}
}
\examples{

data(mtcars)
onesample.plot(variables=d(mpg,cyl,disp,hp,drat,wt,qsec,vs,am,
gear,carb),data=mtcars,type='hist')

onesample.plot(variables=d(mpg,cyl,disp,hp,drat,wt,qsec,vs,am,
gear,carb),data=mtcars,type='box',alpha=1)

} 