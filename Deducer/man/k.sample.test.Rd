\name{k.sample.test}
\alias{k.sample.test}
\title{K Sample Test}
\description{
  Performs a K independent sample test. 
}
\usage{
k.sample.test(formula,data,test=oneway.test,...)
}
\arguments{
 \item{formula}{A formula, the left hand side of which indicated the outcomes, and the right hand side of which contains the factor}
  \item{data}{A data.frame}
  \item{test}{A function whose first argument is a formula with the outcome on the lhs and the factor on the rhs. 
  The second argument should be the data to be used for the formula. The result of the function should be an object of class \code{htest}.}
  \item{\dots}{further arguments for func}  

}
\seealso{
  \code{\link{oneway.test}}
  \code{\link{kruskal.test}}
  \code{\link{wilcox.test}}
}
\value{
  A \code{multi.test} object, representing a table of the results of \code{func} applied to each of the variables.
}  
\examples{
dat<-data.frame(a=rnorm(100),b=rnorm(100),c=rnorm(100),d=cut(rnorm(100),4))
k.sample.test(d(a,b)~d,dat)
k.sample.test(dat[,-4]~dat$d,var.equal=TRUE)
k.sample.test(d(a,c)~d,dat,kruskal.test)
} 