\name{one.sample.test}
\alias{one.sample.test}
\title{One Sample Test}
\description{
  Performs a one sample test. 
}
\usage{
one.sample.test(variables,data=NULL,test=t.test,...)
}
\arguments{
  \item{variables}{A variable or dataframe of variables}
  \item{data}{The data frame in which variables is evaluated}
  \item{test}{A function whose first argument is the sample to be tested,
  and whose result is an object of class \code{htest}.}
  \item{\dots}{further arguments for func}  
}
\seealso{
  \code{\link{t.test}}
  \code{\link{shapiro.test}}
}
\value{
  A \code{multi.test} object, representing a table of the results of \code{test} applied to each of the variables.
}  
\examples{
data(anorexia)

#are subjects' weights at baseline and endpoint significantly different from normal
one.sample.test(variables=d(Prewt,Postwt),
	data=anorexia,
	test=shapiro.test)

#does CBT work at increasing mean wt
anorexia.sub<-subset(anorexia,Treat=="CBT")
one.sample.test(variables=Postwt-Prewt,
	data=anorexia.sub,
	test=t.test)

} 