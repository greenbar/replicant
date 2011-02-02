// Copyright 2011 Kiel Hodges
package replicant

case class ArgList[Args, Next <: Replicant[Next]](private val next: Next) extends Replicant[ArgList[Args, Next]] {

  def apply(args: Args): Next = next.withArgs(args)

  private[replicant] def withArgs[NewArgs](newArgs: NewArgs): ArgList[Args, Next] = new ArgList(next.withArgs(newArgs))

}
  
