 \name{perm.t.test}
\alias{perm.t.test}
\title{
  Permutation t-test
}
\description{
  Two Sample t-test via monte-carlo permutation
}
\usage{
perm.t.test(x,y,statistic=c("t","mean"),
			alternative=c("two.sided", "less", "greater"), midp=TRUE, B=10000)
}
\arguments{
  \item{x}{a numeric vector containing the first sample}
  \item{y}{a numeric vector containing the second sample}
  \item{statistic}{The statistic to be permuted. See details}
  \item{alternative}{The alternative hypothesis}
  \item{midp}{should the mid p-value be used}
  \item{B}{The number of monte-carlo samples to be generated}
}
\details{
This function performs a two sample permutation test. If the mean is permuted, then the test assumes exchangability
between the two samples. if the t-statistic is used, the test assumes either exchangability or a sufficiently large
sample size. Because there is little lost in the way of power, and the assumptions are weaker, the t-statistic is
used by default.
}
\value{
A list with class "htest" containing the following components:
\item{statistic}{The observed value of the statistic. }
\item{p.value}{the p-value for the test.}
\item{method}{a character string indicating the type of test performed.}
\item{data.name}{a character string giving the name(s) of the data.}
\item{B}{The number of samples generated}
\item{alternative}{the direction of the test}
}
\seealso{
  \code{\link{t.test}}
}
\examples{
perm.t.test(rnorm(100),runif(100,-.5,.5))
}