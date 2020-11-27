using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Identity;


namespace BookingSystem.Models
{
    public class Customer
    {
        [Key]
        public string Id { get; set; }
        public IEnumerable<Appointment> Appointments { get; set; }

    }
}
