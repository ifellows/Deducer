\name{sort.data.frame}
\alias{sort.data.frame}
\title{
  Sort Data
}
\description{
    Sorts a data frame
}
\usage{
\method{sort}{data.frame}(x, decreasing, by, ...)
}
\arguments{
  \item{x}{A \code{data.frame} to be sorted}
  \item{decreasing}{unused}
  \item{by}{A character, a one sided formula, or an expression indicating the sorting order}
  \item{\dots}{further arguments}  
}
\details{
  If \code{by} is a formula, or a character vector coerce-able into a formula, 
  \code{x} is sorted by each element of the formula, with ties broken by subsequent elements. 
  Elements preceded by a '-' indicate descending order, otherwise ascending order is used. Parentheses or any formula
  operator other than + and - are ignored, so sorting by \code{a*b} will sort based on the product of a and b.
  
  If \code{by} is not a formula, a \code{~} is appended to the left hand side of the call, and coerced into
  a formula.
  
  The decreasing argument is included for generic method consistency, and is not used.
}
\author{
Ian Fellows adapted from code by Ari Friedman and Kevin Wright
}
\value{
  returns \code{x}, sorted.
}
\seealso{
  \code{\link{sort}}
  \code{\link{order}}
}
\examples{
data(mtcars)

#sort by the number of cylenders
sort(mtcars, by= ~cyl)
sort(mtcars, by= cyl) #identical: no need for ~

#sort in descending order
sort(mtcars, by= -cyl)

#break ties with horse power
sort(mtcars,by= cyl +hp )
sort(mtcars,by= cyl -hp )

#randomly permute the data
sort(mtcars,by= rnorm(nrow(mtcars)) )

#reverse order
sort(mtcars,by= nrow(mtcars):1 )

#sort by squared deviation from mean hp
sort(mtcars,by= -(hp-mean(hp))^2 )
sort(mtcars,by= "-(hp-mean(hp))^2" ) #identical
}






