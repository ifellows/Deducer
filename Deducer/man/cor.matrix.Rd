\name{cor.matrix}
\alias{cor.matrix}
\title{cor.matrix}
\description{
  Creates a correlation matrix 
}
\usage{
cor.matrix(variables,with.variables,data=NULL,test=cor.test,...)
}
\arguments{
  \item{variables}{variables}
  \item{with.variables}{An optional set of variables to correlate with \code{variables}. If
  nothing is specified, all variables in \code{variables} are correlated with themselves. }
  \item{data}{A data.frame from which the variables and factor will be selected.}
  \item{test}{A function whose first two arguments are the variables upon which the correlation will be calculated,
  and whose result is an object of class \code{htest}.}
  \item{\dots}{further arguments for \code{test}.}  
}
\seealso{
  \code{\link{cor.test}}
  \code{\link{as.matrix.cor.matrix}}
}
\value{
  A \code{multi.test} object, representing a table of the results of \code{func} 
  applied to each of the variables.
}  
\examples{
dat<-data.frame(aa=rnorm(100),bb=rnorm(100),cc=rnorm(100),dd=rnorm(100))
dat$aa<-dat$aa+dat$dd
dat$cc<-dat$cc+dat$aa
cor.matrix(dat,test=cor.test)
cor.matrix(d(aa,cc),data=dat,test=cor.test,method="kendall")
cor.matrix(d(aa,cc),d(dd,bb),data=dat,test=cor.test,method="spearman")
} 