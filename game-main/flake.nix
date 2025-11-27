{
  inputs.nixpkgs.url = "github:NixOS/nixpkgs/nixpkgs-unstable";

  outputs = { nixpkgs, ... }: {
    devShells =
      builtins.mapAttrs
        (system: pkgs: {
          default = pkgs.mkShell {
            packages = [ pkgs.gradle ];
            LD_LIBRARY_PATH = pkgs.lib.makeLibraryPath [ pkgs.libglvnd ];
          };
        })
        nixpkgs.legacyPackages;
  };
}