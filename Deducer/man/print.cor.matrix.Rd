 \name{print.cor.matrix}
\alias{print.cor.matrix}
\title{Print method}
\description{
  Print object of class \code{"cor.matrix"} in nice layout.
}
\usage{
\method{print}{cor.matrix}(x,digits=4,N=TRUE,CI=TRUE,stat=TRUE,p.value=TRUE,...)
}
\arguments{
  \item{x}{Object of class \code{"cor.matrix"}.}
  \item{digits}{Number of digits to round to.}
  \item{N}{Logical. print a row for sample size.}
  \item{CI}{Logical. print a row for confidence intervals if they exist.}
  \item{stat}{Logical. print a row for test statistics.}
  \item{p.value}{Logical. print a row for p-values.}
  \item{\dots}{further arguments}  
}
\value{
  none
} 