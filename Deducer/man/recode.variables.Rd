\name{recode.variables}
\alias{recode.variables}
\title{
  Recode
}
\description{
    Recodes a set of variables according to a set of rules
}
\usage{
recode.variables(data,recodes)
}
\arguments{
  \item{data}{A \code{data.frame} to be recoded}
  \item{recodes}{ Definition of the recoding rules. See details}
}
\details{
\code{recodes} contains a set of recoding rules separated by ";".
There are three different types of recoding rules:

1. The simplest codes one value to another. If we wish to recode 1 into 2, we could use the rule \code{"1->2;"}.

2. A range of values can be coded to a single value using \code{"1:3->4;"}. 
This rule would code all values between 1 and 3 inclusive into 4. For factors, a value is 
between two levels if it is between them in the factor ordering. 
One sided ranges can be specified using the Lo and Hi key words (e.g.\code{"Lo:3->0; 4:Hi->1"})

3. Default conditions can be coded using "else." For example, if we wish to recode all 
values >=0 to 1 and all values <0 to missing, we could use (\code{"0:Hi->1; else->NA"})
}
\value{
  returns a recoded \code{data.frame}
}
\author{
Ian Fellows adapted from code by John Fox
}
\seealso{
  \code{\link{cut}}
  \code{\link{recode}} in package 'car' 
}
\examples{
data<-data.frame(a=rnorm(100),b=rnorm(100),male=rnorm(100)>0)
recode.variables(data[c("a","b")] , "Lo:0 -> 0;0:Hi -> 1;")
data[c("male")] <- recode.variables(data[c("male")] , "1 -> 'Male';0 -> 'Female';else -> NA;")
}
