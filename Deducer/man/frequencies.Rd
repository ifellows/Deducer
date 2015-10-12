\name{frequencies}
\alias{frequencies}
\title{
  Frequency Tables
}
\description{
  Creates a set of frequency tables.
}
\usage{
frequencies(data,r.digits=1)
}
\arguments{
  \item{data}{A data.frame containing the variables on which to run frequencies}
  \item{r.digits}{how many digits should the percentages be rounded to}
}
\value{
  Returns a list of \code{freq.table} objects. One for each variable in \code{data}.
}
\seealso{
  \code{\link{table}}
  \code{\link{xtabs}}
  \code{\link{descriptive.table}}
  \code{\link{prop.table}}
}
\examples{
dat<-data.frame(rnorm(100)>0,trunc(runif(100,0,5)))
##rounding to 1
frequencies(dat)
##rounding to 4
frequencies(dat,4)
}
