\name{two.sample.test}
\alias{two.sample.test}
\title{Two Sample Test}
\description{
  Performs a two independent sample test. 
}
\usage{
two.sample.test(formula,data=NULL,test=t.test,...)
}
\arguments{
  \item{formula}{A formula, the left hand side of which indicated the outcomes, and the right hand side of which contains the factor}
  \item{data}{A data.frame}
  \item{test}{A function whose first two arguments are the two-samples to be tested,
  and whose result is an object of class \code{htest}.}
  \item{\dots}{further arguments for test}  
}
\seealso{
  \code{\link{t.test}}
  \code{\link{ks.test}}
  \code{\link{wilcox.test}}
}
\value{
  A \code{multi.test} object, representing a table of the results of \code{test} applied to each of the variables.
}  
\examples{
dat<-data.frame(a=rnorm(100),b=rnorm(100),c=rnorm(100),d=rnorm(100)>(-.5))
two.sample.test(d(a,b) ~ d,dat,ks.test)
two.sample.test(a ~ dich(b,cut=0) ,dat,t.test)
two.sample.test(d(a^2,abs(b),c)~d,dat,wilcox.test)
}