using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Azure.Identity;
using Azure.Security.KeyVault.Secrets;

namespace BookingSystem.Services.EmailSender
{
    public class AuthMessageSenderOptions
    {
        public AuthMessageSenderOptions()
        {
            var kvUri = "https://bookingsystemvault.vault.azure.net/";
            var client = new SecretClient(new Uri(kvUri), new DefaultAzureCredential());

            this.SendGridKey = client.GetSecret("SEND-GRID-KEY").Value.Value;
        }
        public string SendGridUser { get; set; }
        public string SendGridKey { get; set; }
    }
}
