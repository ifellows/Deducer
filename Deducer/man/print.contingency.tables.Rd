\name{print.contingency.tables}
\alias{print.contingency.tables}
\title{Print method}
\description{
  Print object of class \code{"contingency.tables"} in nice layout.
}
\usage{
\method{print}{contingency.tables}(x,digits=3,prop.r=TRUE,prop.c=TRUE,prop.t=TRUE,
						expected.n=FALSE,no.tables=FALSE,...)
}
\arguments{
  \item{x}{Object of class \code{"contin.table"}.}
  \item{digits}{Number of digits to round to.}
  \item{prop.r}{Logical. print row proportions.}
  \item{prop.c}{Logical. print column proportions.}
  \item{prop.t}{Logical. print proportions.}
  \item{expected.n}{Logical print expected cell counts.}
  \item{no.tables}{Logical. Suppress tables}
  \item{\dots}{further arguments}  
}
\value{
  none
}  