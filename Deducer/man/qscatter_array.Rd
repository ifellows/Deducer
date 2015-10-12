\name{qscatter_array}
\alias{qscatter_array}
\title{qscatter_array}
\description{
  Creates an array of scatterplots
}
\usage{
qscatter_array(variables,with.variables,data,x.lab="",y.lab="",
				main="Correlation Array",common.scales=TRUE,alpha=.25)
}
\arguments{
  \item{variables}{variables}
  \item{with.variables}{An optional set of variables to correlate with \code{variables}. If
  nothing is specified, all variables in \code{variables} are correlated with themselves. }
  \item{data}{A data.frame from which the variables will be selected.}
  \item{x.lab}{A label for the x axis}
  \item{y.lab}{A label for the y axis}
  \item{main}{A label for the plot}
  \item{common.scales}{should common x and y scales be used.}
  \item{alpha}{alpha transparency}
}

\examples{
data(mtcars)
qscatter_array(d(cyl,disp,hp,drat),
	data=mtcars) + geom_smooth(method="lm")
qscatter_array(d(cyl,disp,hp,drat),d(wt,carb),data=mtcars,common.scales=FALSE)
} 
