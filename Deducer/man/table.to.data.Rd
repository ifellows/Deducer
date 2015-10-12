\name{table.to.data}
\alias{table.to.data}
\title{
  Table --> data.frame
}
\description{
  Creates a data.frame from a table
}
\usage{
table.to.data(x)
}
\arguments{
  \item{x}{A matrix or table representing the cross tabulation of two variables}

}
\value{
  A two column data.frame where each row is an observation and each column is a variable.
}
\seealso{
  \code{\link{xtabs}}
}
\examples{
tab<-matrix(c(4,5,6,9,7,3),ncol=3)
tab
table.to.data(tab)

} 