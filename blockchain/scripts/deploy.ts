import hre from "hardhat";
import { ethers } from "ethers";
import { config } from "dotenv";
import { resolve } from "path";

config({ path: resolve(process.cwd(), ".env") });

async function main() {
  const provider = new ethers.JsonRpcProvider(process.env.AMOY_RPC_URL);
  const wallet = new ethers.Wallet(process.env.PRIVATE_KEY!, provider);

  const artifact = await hre.artifacts.readArtifact("ValidateDocumentHash");
  const factory = new ethers.ContractFactory(artifact.abi, artifact.bytecode, wallet);

  const contract = await factory.deploy();
  await contract.waitForDeployment();
  console.log("ValidateDocumentHash deployado em:", await contract.getAddress());
}

main().catch((error) => {
  console.error(error);
  process.exit(1);
});