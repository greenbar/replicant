package replicant

private[replicant] trait Replicant[Self] {

  private[replicant] def withArgs[NewArgs](args: NewArgs): Self

}
