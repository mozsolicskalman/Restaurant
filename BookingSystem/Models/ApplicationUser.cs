using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Identity;


namespace BookingSystem.Models
{
    public class ApplicationUser : IdentityUser
    {
        public virtual Customer Customer { get; set; }
        public virtual Seller Supplier { get; set; }
    }
}
